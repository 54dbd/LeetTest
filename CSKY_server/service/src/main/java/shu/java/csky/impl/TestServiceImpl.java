package shu.java.csky.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import shu.java.csky.TestService;
import shu.java.csky.dao.TestMapper;
import shu.java.csky.dao.SearchMapper;
import shu.java.csky.entity.Test;
import shu.java.csky.entity.Search;
import shu.java.csky.vo.*;
import shu.java.csky.vo.page.TestPageVo;
import shu.java.csky.vo.param.QueryPageParam;
import org.apache.commons.lang3.StringUtils;
import shu.java.csky.vo.param.TestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 54dbd
 * @date 2022/6/23 8:40
 */
@Service
public class TestServiceImpl implements TestService {

    @Resource
    private TestMapper testMapper;

    @Resource
    private SearchMapper searchMapper;


    @Override
    public ResultVO getTestVoList(Page<Test> pageParam){
        QueryWrapper<Test> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("tid");
        testMapper.selectPage(pageParam, wrapper);
        ArrayList<TestVo> testVos = new ArrayList<>();
        for(Test record:pageParam.getRecords()){
            TestVo testVo = new TestVo(
                    record.getTid(),
                    record.getSname(),
                    record.getDifficulty(),
                    record.getAccuracy(),
                    record.getPoints(),
                    record.getQtype(),
                    record.getQuestion(),
                    record.getAtype(),
                    record.getNum(),
                    record.getCorrectnum());
            testVos.add(testVo);
        }
        TestPageVo testPageVo = new TestPageVo();
        testPageVo.setTestVoList(testVos);
        BeanUtils.copyProperties(pageParam, testPageVo);
        return new ResultVO(ResStatus.OK, "题目分页信息查询成功",testPageVo);
    }

    @Override
    public Test getTestDetailByTid(Integer tid){
        return testMapper.selectById(tid);
    }

    @Override
    public List<TestNameVo> getTestNameList() {
        List<Test> testList = testMapper.selectList(null);
        List<TestNameVo> testNameVoList = new ArrayList<>();
        for (Test test : testList) {
            TestNameVo testNameVo = new TestNameVo();
            BeanUtils.copyProperties(test, testNameVo);
            testNameVoList.add(testNameVo);
        }
        return testNameVoList;
    }

    @Override
    public ResultVO fuzzyQueryTest(QueryPageParam queryPageParam) {
        String text = queryPageParam.getText();
        Integer uid = queryPageParam.getUid();
        // 若text为空，直接返回
        if (text == null)
            return new ResultVO(ResStatus.OK, "text为空", null);

        QueryWrapper<Test> wrapper = new QueryWrapper<>();
        wrapper.like("sname", text)
                .or()
                .like("question", text)
                .or()
                .like("difficulty", text)
                .orderByAsc("tid");
        IPage<Test> testPage = new Page<>();
        BeanUtils.copyProperties(queryPageParam, testPage);
        testMapper.selectPage(testPage, wrapper);
        TestPageVo testPageVo = new TestPageVo();
        BeanUtils.copyProperties(testPage, testPageVo);
        ArrayList<TestVo> testVos = new ArrayList<>();
        for (Test record : testPage.getRecords()) {
            TestVo testVo = new TestVo();
            BeanUtils.copyProperties(record, testVo);
            testVos.add(testVo);
        }
        // 若查到数据，插入search表
        if (!testVos.isEmpty() && (uid != null))
            searchMapper.insert(new Search(0, uid, text));
        testPageVo.setTestVoList(testVos);
        return new ResultVO(ResStatus.OK, "考试模糊查询分页信息返回成功", testPageVo);
    }


    @Override
    public ResultVO setNumByAid(TestParam testParam) {
        String sname = testParam.getSname();
        Integer num = testParam.getNum();
        Integer correctnum = testParam.getCorrectnum();
        double accuracy = testParam.getAccuracy();

        QueryWrapper<Test> wrapper = new QueryWrapper<>();
        wrapper.eq("sname",sname);
        Test test = testMapper.selectOne(wrapper);
        Test newTest = new Test();
        newTest.setTid(test.getTid());
        newTest.setAtype(test.getAtype());

        if(accuracy!=0){
            newTest.setAccuracy(accuracy);
        }else{
           newTest.setAccuracy(test.getAccuracy());
        }
        if (num!=null)
        {
            newTest.setNum(num);
        }else{
            newTest.setNum(test.getNum());
        }
        if(correctnum!=null){
            newTest.setCorrectnum(correctnum);
        }else{
            newTest.setCorrectnum(test.getCorrectnum());
        }
        testMapper.updateById(newTest);
        return new ResultVO(200, "修改做题数量以及正确数量成功", newTest);



    }


}
