package shu.java.csky.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import shu.java.csky.TestHistoryService;
import shu.java.csky.dao.TestHistoryMapper;
import shu.java.csky.dao.TestMapper;
import shu.java.csky.entity.Test;
import shu.java.csky.entity.TestComment;
import shu.java.csky.entity.TestHistory;
import shu.java.csky.vo.ResStatus;
import shu.java.csky.vo.ResultVO;
import shu.java.csky.vo.param.TestHistoryParam;
import shu.java.csky.utils.TextUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestHistoryServiceImpl implements TestHistoryService {

    @Resource
    TestHistoryMapper testHistoryMapper;

    @Resource
    private TestMapper testMapper;

    @Override
    public List<TestHistory> getProblemCollectionBySnameAndUserid(TestHistoryParam testHistoryParam) {
        Integer userid = testHistoryParam.getUserid();
        String sname = testHistoryParam.getSname();
        QueryWrapper<TestHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("sname", sname).eq("userid", userid);
        return testHistoryMapper.selectList(wrapper);
    }

    @Override
    public TestHistory submitChoice(TestHistoryParam testHistoryParam) {
        TestHistory history = new TestHistory();
        QueryWrapper<Test> testWrapper = new QueryWrapper<>();
        // 获取正确答案
        testWrapper.eq("sname", testHistoryParam.getSname());
        Test test = testMapper.selectOne(testWrapper);
        String answer = test.getAnswer();
        // 获取用户的答案
        String userAnswer = testHistoryParam.getWronganswer();
        boolean correct;
        if (answer.charAt(answer.length() - 1) == ' ') {
            answer = answer.substring(0, answer.length() - 1);
        }
        // 判断用户答案是否正确
        if (userAnswer.length() != answer.length()) {
            correct = false;
        } else {
            List<String> answerList = new ArrayList<>(Arrays.asList(answer.split("")));
            List<String> userAnswerList = new ArrayList<>(Arrays.asList(userAnswer.split("")));
            answerList.removeAll(userAnswerList);
            correct = answerList.size() == 0;
        }
        testHistoryParam.setIscorrect(correct);
        BeanUtils.copyProperties(testHistoryParam, history);
        testHistoryMapper.insert(history);
        return history;
    }

    @Override
    public TestHistory submitText(TestHistoryParam testHistoryParam) {
        TestHistory history = new TestHistory();
        QueryWrapper<Test> testWrapper = new QueryWrapper<>();
        // 获取正确答案
        testWrapper.eq("sname", testHistoryParam.getSname());
        Test test = testMapper.selectOne(testWrapper);
        String answer = test.getAnswer();
        // 获取用户的答案
        String userAnswer = testHistoryParam.getWronganswer();
        boolean correct;

        //获得答案与回答的匹配程度,30%权重
        double weight_1 = TextUtils.strSimilarity2Percent(userAnswer, answer) * 0.3;
        //获得答案与回答的模糊程度,70%权重
        double weight_2 = TextUtils.fuzzyMatch(userAnswer, answer) * 0.7;
        //获得综合的权重
        double weight = weight_1 + weight_2;
        double sigmoid = 1 / (1 + Math.exp(-8 * (weight - 0.5)));
        //获得综合的得分
        int score = (int) (sigmoid * 100 / 20 + 0.5);
        correct = score >= 4;

        testHistoryParam.setIscorrect(correct);
        testHistoryParam.setScore(score);
        BeanUtils.copyProperties(testHistoryParam, history);
        testHistoryMapper.insert(history);
        return history;
    }

    @Override
    public List<Test> queryTestByID(int userid) {
        QueryWrapper<TestHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userid).eq("iscorrect", 0);
        wrapper.select("sname, count(sname) as count").groupBy("sname").orderByDesc("count");
        List<TestHistory> testHistories = testHistoryMapper.selectList(wrapper);
        List<Test> tests = new ArrayList<>();
        for (TestHistory testHistory : testHistories) {
            QueryWrapper<Test> testWrapper = new QueryWrapper<>();
            testWrapper.eq("sname", testHistory.getSname());
            tests.add(testMapper.selectOne(testWrapper));
        }
        return tests;
    }

    @Override
    public ResultVO getNumById(Integer userId) {
        QueryWrapper<TestHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userId);
//        System.out.println(testHistoryMapper.selectCount(wrapper));
        return new ResultVO(200, "获取做题数成功", testHistoryMapper.selectCount(wrapper));
    }

    @Override
    public ResultVO getCorrectById(Integer userId) {
        QueryWrapper<TestHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userId).eq("iscorrect", true);
//        System.out.println(testHistoryMapper.selectCount(wrapper));
        return new ResultVO(200, "获取正确题数成功", testHistoryMapper.selectCount(wrapper));
    }

    @Override
    public List<Map.Entry<LocalDate, Integer>> getDayNumById(Integer userId) {
        QueryWrapper<TestHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userId);

        List<TestHistory> testHistories = testHistoryMapper.selectList(wrapper);

        Map<LocalDate, Integer> testCounts = new HashMap<>();

        for (TestHistory testHistory : testHistories) {
            LocalDateTime dateTime = testHistory.getCreatedate().toLocalDateTime();
            LocalDate date = dateTime.toLocalDate();

            Integer count = testCounts.getOrDefault(date, 0);

            count++;
            testCounts.put(date, count);
        }
        List<Map.Entry<LocalDate, Integer>> testCountsByDay = testCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());

        return testCountsByDay;
    }

    @Override
    public List<Map.Entry<LocalDate, Integer>> getDayCorrectNumById(Integer userId) {
        QueryWrapper<TestHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userId);
        List<TestHistory> testHistories = testHistoryMapper.selectList(wrapper);

        Map<LocalDate, Integer> testCounts = new HashMap<>();
        List<LocalDate> allDates = testHistories.stream()
                .map(testHistory -> testHistory.getCreatedate().toLocalDateTime().toLocalDate())
                .distinct()
                .collect(Collectors.toList());
        for (LocalDate date : allDates) {
            testCounts.put(date, 0);
        }

        wrapper.eq("iscorrect", 1);
        testHistories = testHistoryMapper.selectList(wrapper);
        for (TestHistory testHistory : testHistories) {
            LocalDateTime dateTime = testHistory.getCreatedate().toLocalDateTime();
            LocalDate date = dateTime.toLocalDate();

            Integer count = testCounts.getOrDefault(date, 0);

            count++;
            testCounts.put(date, count);
        }
        List<Map.Entry<LocalDate, Integer>> testCountsByDay = testCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());

        return testCountsByDay;
    }

    @Override
    public List<Map.Entry<String, Integer>> getPointNumById(Integer userId) {
        QueryWrapper<TestHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userId).eq("iscorrect", 0);
        wrapper.select("sname, count(sname) as count").groupBy("sname").orderByDesc("count");
        List<TestHistory> testHistories = testHistoryMapper.selectList(wrapper);

        List<Test> tests = new ArrayList<>();
        for (TestHistory testHistory : testHistories) {
            QueryWrapper<Test> testWrapper = new QueryWrapper<>();
            testWrapper.eq("sname", testHistory.getSname());
            tests.add(testMapper.selectOne(testWrapper));
        }


        // 统计每个知识点的错误次数
        Map<String, Integer> testCounts = new HashMap<>();
        for (Test item : tests) {
            String points = item.getPoints();
            if (points != null) {
                Integer count = testCounts.getOrDefault(points, 0);
                count++;
                testCounts.put(points, count);
            }
        }

        return testCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());
    }
}
