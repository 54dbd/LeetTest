package com.shu.leettest.vo.page;

import com.shu.leettest.vo.param.ArticleBriefParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageVo extends Page {
    List<ArticleBriefParam> articleBriefParams;
}
