package com.shu.leettest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.TestCommentNameVo;
import com.shu.leettest.vo.param.TestCommentParam;
import com.shu.leettest.entity.TestComment;

import java.util.List;

public interface TestCommentService {

    /**
     * 根据题目的Id，获取题目的评论
     */
    TestComment getCommentDetailByCommentId(Integer commentid);

    List<TestComment> getTestCommentByQuestionid(String questionid);

    ResultVO getCommentVoList(Page<TestComment> pageParam);

    List<TestCommentNameVo> getCommentNameList();

    ResultVO insertComment(TestCommentParam commentParam);


}
