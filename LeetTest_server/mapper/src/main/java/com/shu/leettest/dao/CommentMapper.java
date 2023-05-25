package com.shu.leettest.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shu.leettest.vo.CommentVo;
import org.apache.ibatis.annotations.Param;
import com.shu.leettest.entity.Comment;

import java.util.List;


public interface CommentMapper extends BaseMapper<Comment> {
    List<CommentVo> getAllByArticleId(@Param("articleId") String articleId);
}