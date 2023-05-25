package com.shu.leettest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.param.CommentParam;
import com.shu.leettest.vo.param.IdParam;
import com.shu.leettest.entity.Comment;


public interface CommentService {
    /**
     * 根据文章的Id，获取文章的评论
     *  @param articleId 文章的Id
     * @param userId 用户的Id
     * @param page      分页参数
     */
    ResultVO getCommentByArticleId(Integer articleId, Integer userId, Page<Comment> page);

    ResultVO addComment(CommentParam commentParam);

    ResultVO addLike(IdParam idParam);

    ResultVO revokeLike(IdParam idParam);

    ResultVO getConditionByAIdAndUId(Integer cid, Integer uid);

    Long getCommentCountByAid(Integer aid);
}
