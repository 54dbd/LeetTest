package com.shu.leettest.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shu.leettest.vo.ArticleVo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageVo2 extends Page {
    public ArticlePageVo2(long current, long pages, long size, long total, List<ArticleVo> articleVoList) {
        super(current, pages, size, total);
        this.articleVoList = articleVoList;
    }

    List<ArticleVo> articleVoList;
}
