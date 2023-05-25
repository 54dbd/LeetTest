package com.shu.leettest.controller;

import com.shu.leettest.vo.param.*;
import org.springframework.web.bind.annotation.*;
import com.shu.leettest.ArticleService;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.param.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    ArticleService articleService;

    @GetMapping("/{id}")
    public ResultVO getArticleById(@PathVariable String id) {
        return articleService.getArticleById(id);
    }

    @PostMapping("getRecruitInfo")
    public ResultVO getRecruitInfo(@RequestBody RecruitInfoPageParam articlePageParam) {
        return articleService.getRecruitInfoBySId(articlePageParam);
    }

    @PostMapping("/add")
    public ResultVO saveArticle(@RequestBody ArticleParam articleParam) {
        return new ResultVO(200, "成功", articleService.insertArticle(articleParam));
    }

    @PostMapping("addLikes")
    public ResultVO addLikes(@RequestBody IdParam idParam) {
        return articleService.insertLikesByAidAndUid(idParam);
    }

    @PostMapping("addHistory")
    public ResultVO addHistory(@RequestBody IdParam idParam) {
        return articleService.insertHistoryByAidAndUid(idParam);
    }

    @PostMapping("addRepost")
    public ResultVO addRepost(@RequestBody IdParam idParam) {
        return articleService.insertRepostByAidAndUid(idParam);
    }

    @PostMapping("addFavorite")
    public ResultVO addFavorite(@RequestBody IdParam idParam) {
        return articleService.insertFavoriteByAidAndUid(idParam);
    }

    @PostMapping("/query")
    public ResultVO fuzzyQueryArticle(@RequestBody QueryPageParam queryPageParam) {
        return articleService.fuzzyQueryArticle(queryPageParam);
    }

    @PostMapping("/condition")
    public ResultVO getConditionByAIdAndUId(@RequestBody ConditionParam conditionParam) {
        Integer aid = conditionParam.getAid();
        Integer uid = conditionParam.getUid();
        return articleService.getConditionByAIdAndUId(aid, uid);
    }

    @GetMapping("/getConditionInfo/{aid}")
    public ResultVO getConditionInfo(@PathVariable Integer aid) {
        return articleService.getConditionInfo(aid);
    }

    @PostMapping("revokeLikes")
    public ResultVO revokeLikes(@RequestBody IdParam idParam) {
        return articleService.revokeLikesByAidAndUid(idParam);
    }

    @PostMapping("revokeFavorite")
    public ResultVO revokeFavorite(@RequestBody IdParam idParam) {
        return articleService.revokeFavoriteByAidAndUid(idParam);
    }

}
