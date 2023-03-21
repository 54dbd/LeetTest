package shu.java.csky.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shu.java.csky.entity.Comment;
import shu.java.csky.vo.CommentVo;

import java.util.List;


public interface CommentMapper extends BaseMapper<Comment> {
    List<CommentVo> getAllByArticleId(@Param("articleId") String articleId);
}