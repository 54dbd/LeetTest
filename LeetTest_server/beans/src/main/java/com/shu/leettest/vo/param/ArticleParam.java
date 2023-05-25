package com.shu.leettest.vo.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleParam {
    private String title;
    private String content;
    private Integer schoolId;
    private Integer authorId;
    private List<Integer> tagIdList;
}
