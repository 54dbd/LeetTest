package shu.java.csky.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shu.java.csky.entity.Articles;
import shu.java.csky.entity.TagName;

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
