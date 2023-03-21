package shu.java.csky.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shu.java.csky.SearchService;
import shu.java.csky.dao.SearchMapper;
import shu.java.csky.vo.ResultVO;

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
