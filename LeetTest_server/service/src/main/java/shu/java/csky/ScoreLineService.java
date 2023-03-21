package shu.java.csky;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import shu.java.csky.entity.Scoreline;
import shu.java.csky.vo.ResultVO;


public interface ScoreLineService {
    ResultVO getScorelineBySid(Integer sid);

    ResultVO getScorelinePage(Page<Scoreline> scorelineIPage);
}
