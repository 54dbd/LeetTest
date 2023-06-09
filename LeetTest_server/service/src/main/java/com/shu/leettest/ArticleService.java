package com.shu.leettest;

import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.param.ArticleParam;
import com.shu.leettest.vo.param.IdParam;
import com.shu.leettest.vo.param.QueryPageParam;
import com.shu.leettest.vo.param.RecruitInfoPageParam;


public interface ArticleService {
    /**
     * 根据文章的Id，获取文章
     *
     * @param articleId 文章的Id
     */
    ResultVO getArticleById(String articleId);

    /**
     * 根据学校的Id，获取文章列表，并分页展示
     *
     * @param articlePageParam
     */
    ResultVO getRecruitInfoBySId(RecruitInfoPageParam articlePageParam);

    Long getLikesCountByAid(Integer aid);

    Long getHistoryCountByAid(Integer aid);

    Long getRepostCountByAid(Integer aid);

    Long getFavoriteCountByAid(Integer aid);

    ResultVO insertLikesByAidAndUid(IdParam idParam);

    ResultVO insertHistoryByAidAndUid(IdParam idParam);

    ResultVO insertRepostByAidAndUid(IdParam idParam);

    ResultVO insertFavoriteByAidAndUid(IdParam idParam);

    ResultVO insertArticle(ArticleParam articleParam);

    ResultVO fuzzyQueryArticle(QueryPageParam queryPageParam);

    ResultVO getConditionByAIdAndUId(Integer aid, Integer uid);

    ResultVO getFavoriteByUId(IdParam idParam);

    ResultVO getWorkByUId(IdParam idParam);

    ResultVO getConditionInfo(Integer aid);

    ResultVO revokeLikesByAidAndUid(IdParam idParam);

    ResultVO revokeFavoriteByAidAndUid(IdParam idParam);

    ResultVO getArticleTitleByTid(Integer tid);
}
