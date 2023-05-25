package com.shu.leettest.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leettest.dao.SearchMapper;
import com.shu.leettest.dao.TestMapper;
import com.shu.leettest.entity.Search;
import com.shu.leettest.vo.ResStatus;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.TestNameVo;
import com.shu.leettest.vo.TestVo;
import com.shu.leettest.vo.page.TestPageVo;
import com.shu.leettest.vo.param.QueryPageParam;
import com.shu.leettest.vo.param.TestParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.shu.leettest.TestService;
import com.shu.leettest.entity.Test;
import com.shu.leettest.vo.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
