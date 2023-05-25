package com.shu.leettest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shu.leettest.SearchService;
import com.shu.leettest.vo.ResultVO;

import javax.annotation.Resource;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Resource
    SearchService searchService;

    @GetMapping("list/{uid}")
    public ResultVO getSearchHistoryByUId(@PathVariable Integer uid) {
        return searchService.getSearchHistoryByUId(uid);
    }

}
