package com.shu.leettest.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leettest.dao.TestCommentMapper;
import com.shu.leettest.dao.UserMapper;
import com.shu.leettest.entity.TestComment;
import com.shu.leettest.vo.ResStatus;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.TestCommentNameVo;
import com.shu.leettest.vo.TestCommentVo;
import com.shu.leettest.vo.page.TestCommentPageVo;
import com.shu.leettest.vo.param.TestCommentParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.shu.leettest.TestCommentService;
import com.shu.leettest.entity.*;
import com.shu.leettest.vo.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestCommentServiceImpl implements TestCommentService {

    @Resource
    TestCommentMapper testCommentMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public ResultVO getCommentVoList(Page<TestComment> pageParam){
        QueryWrapper<TestComment> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("commentid");
        testCommentMapper.selectPage(pageParam, wrapper);

        ArrayList<TestCommentVo> commentVos = new ArrayList<>();
        for (TestComment record : pageParam.getRecords()) {
            //User user = userMapper.selectById(record.getUser_id());
            TestCommentVo commentVo = new TestCommentVo( record.getCommentid(), record.getQuestionid(),record.getUserid(),record.getCommenttext(),record.getTitle());
            commentVos.add(commentVo);
        }
        TestCommentPageVo testCommentPageVo = new TestCommentPageVo();
        testCommentPageVo.setTestCommentVoList(commentVos);
        BeanUtils.copyProperties(pageParam, testCommentPageVo);
        return new ResultVO(ResStatus.OK, "题目评论信息查询成功", testCommentPageVo);
    }

    @Override
    public TestComment getCommentDetailByCommentId(Integer commentid){
        return testCommentMapper.selectById(commentid);
    }

    @Override
    public List<TestComment> getTestCommentByQuestionid(String questionid){
        QueryWrapper<TestComment> wrapper = new QueryWrapper<>();
        wrapper.eq("questionid",questionid);
        return testCommentMapper.selectList(wrapper);
    }

    @Override
    public List<TestCommentNameVo> getCommentNameList(){
        List<TestComment> testCommentList = testCommentMapper.selectList(null);
        List<TestCommentNameVo> testCommentNameVoList = new ArrayList<>();
        for (TestComment comment : testCommentList) {
            TestCommentNameVo testCommentNameVo = new TestCommentNameVo();
            BeanUtils.copyProperties(comment, testCommentNameVo);
            testCommentNameVoList.add(testCommentNameVo);
        }
        return testCommentNameVoList;
    }


    private TestCommentVo getArticleVo(String questionId) {
        QueryWrapper<TestComment> articlesQueryWrapper = new QueryWrapper<>();
        articlesQueryWrapper.eq("questionid", questionId);
        TestComment comment = testCommentMapper.selectOne(articlesQueryWrapper);

        TestCommentVo commentVo = new TestCommentVo(comment.getCommentid(), comment.getQuestionid(),comment.getUserid(),comment.getCommenttext(),comment.getTitle());
        return commentVo;
    }

    @Override
    public ResultVO insertComment(TestCommentParam commentParam) {
        TestComment comment = new TestComment();
        BeanUtils.copyProperties(commentParam, comment);
        testCommentMapper.insert(comment);
        return new ResultVO(ResStatus.OK, "成功", comment);
    }



}
