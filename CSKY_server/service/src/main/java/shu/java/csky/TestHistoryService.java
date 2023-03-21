package shu.java.csky;

import shu.java.csky.entity.TestHistory;
import shu.java.csky.vo.ResultVO;
import shu.java.csky.vo.param.TestCommentParam;
import shu.java.csky.vo.param.TestHistoryParam;

import java.util.List;

/**
 * @author 54dbd
 * @Classname TestHistoryService
 * @date 2022/7/7 14:51
 */
public interface TestHistoryService {
    List<TestHistory> getProblemCollectionBySnameAndUserid(TestHistoryParam testHistoryParam);

    ResultVO insertHistory(TestHistoryParam testHistoryParam);

    ResultVO getNumById(Integer userId);

    ResultVO getCorrectById(Integer userId);
}
