package shu.java.csky.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shu.java.csky.vo.param.ArticleBriefParam;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageVo extends Page {
    List<ArticleBriefParam> articleBriefParams;
}
