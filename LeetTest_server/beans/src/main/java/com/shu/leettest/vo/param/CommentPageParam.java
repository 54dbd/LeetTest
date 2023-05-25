package com.shu.leettest.vo.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shu.leettest.entity.Comment;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPageParam {
    private Integer articleId;
    private Integer userId;
    private Page<Comment> page;
}
