package com.shu.leettest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCommentVo {
    private Integer commentid;
    private String questionid;
    private Integer userid;
    private String commenttext;
    private String title;

}
