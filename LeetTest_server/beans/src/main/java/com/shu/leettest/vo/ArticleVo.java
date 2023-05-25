package com.shu.leettest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shu.leettest.entity.Articles;
import com.shu.leettest.entity.TagName;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {
    private long likesCount;
    private long repostCount;
    private long favoriteCount;
    private long historyCount;
    private List<TagName> tagsList;
    private UserVo author;
    private Articles article;
}
