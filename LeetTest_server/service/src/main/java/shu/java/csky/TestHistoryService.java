package shu.java.csky;

import shu.java.csky.entity.Test;
import shu.java.csky.entity.TestHistory;
import shu.java.csky.vo.ResultVO;
import shu.java.csky.vo.param.TestCommentParam;
import shu.java.csky.vo.param.TestHistoryParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author 54dbd
 * @Classname TestHistoryService
 * @date 2022/7/7 14:51
 */
public interface TestHistoryService {
    List<TestHistory> getProblemCollectionBySnameAndUserid(TestHistoryParam testHistoryParam);

    TestHistory submitChoice(TestHistoryParam testHistoryParam);

    TestHistory submitText(TestHistoryParam testHistoryParam);

    List<Test> queryTestByID(int userid);

    ResultVO getNumById(Integer userId);

    ResultVO getCorrectById(Integer userId);

    List<Map.Entry<LocalDate, Integer>> getDayNumById(Integer userId);
    List<Map.Entry<LocalDate, Integer>> getDayCorrectNumById(Integer userId);
}
