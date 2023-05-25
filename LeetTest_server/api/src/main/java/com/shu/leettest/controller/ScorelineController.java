package com.shu.leettest.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.shu.leettest.ScoreLineService;
import com.shu.leettest.entity.Scoreline;
import com.shu.leettest.vo.ResultVO;

import javax.annotation.Resource;


@RestController
@RequestMapping("/scoreline")
public class ScorelineController {
    @Resource
    ScoreLineService scoreLineService;

    @GetMapping("school/{sid}")
    ResultVO getScorelineBySid(@PathVariable Integer sid) {
        return scoreLineService.getScorelineBySid(sid);
    }

    @PostMapping("page")
    ResultVO getScorelinePage(@RequestBody Page<Scoreline> scorelineIPage) {
        return scoreLineService.getScorelinePage(scorelineIPage);
    }
}
