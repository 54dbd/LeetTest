package com.shu.leettest.controller;

import org.springframework.web.bind.annotation.*;
import com.shu.leettest.ArticleService;
import com.shu.leettest.TagService;
import com.shu.leettest.entity.TagName;
import com.shu.leettest.vo.ResStatus;
import com.shu.leettest.vo.ResultVO;

import javax.annotation.Resource;


@RestController
@RequestMapping("/tag")
public class TagController {
    @Resource
    private TagService tagService;
    @Resource
    private ArticleService articleService;

    @GetMapping("/list")
    public ResultVO tagNameList() {
        return new ResultVO(ResStatus.OK, "成功", tagService.tagNameList());
    }

    @PostMapping("addTagName")
    public ResultVO addTag(@RequestBody TagName tagName) {
        return tagService.addTagName(tagName.getTagName());
    }

    @GetMapping("article/{tid}")
    public ResultVO getArticleTitleByTid(@PathVariable Integer tid){
        return articleService.getArticleTitleByTid(tid);
    }

}
