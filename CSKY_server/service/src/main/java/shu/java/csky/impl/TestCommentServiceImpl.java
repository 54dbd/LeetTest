package shu.java.csky.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import shu.java.csky.TestCommentService;
import shu.java.csky.dao.ArticlesMapper;
import shu.java.csky.dao.TestCommentMapper;
import shu.java.csky.dao.UserMapper;
import shu.java.csky.entity.*;
import shu.java.csky.vo.*;
import shu.java.csky.vo.page.SchoolPageVo;
import shu.java.csky.vo.page.TestCommentPageVo;
import shu.java.csky.vo.page.TestPageVo;
import shu.java.csky.vo.param.ArticleParam;
import shu.java.csky.vo.param.IdParam;
import shu.java.csky.vo.param.TestCommentParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 54dbd
 * @Classname TestCommentServiceImpl
 * @date 2022/7/5 20:13
 */
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
