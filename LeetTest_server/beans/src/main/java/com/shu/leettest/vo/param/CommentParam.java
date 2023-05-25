package com.shu.leettest.vo.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentParam {
    private Integer userId;
    private Integer articleId;
    private String content;
    private Integer parentId;
    private Integer toUserId;
}
