package com.shu.leettest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.entity.Scoreline;


public interface ScoreLineService {
    ResultVO getScorelineBySid(Integer sid);

    ResultVO getScorelinePage(Page<Scoreline> scorelineIPage);
}
