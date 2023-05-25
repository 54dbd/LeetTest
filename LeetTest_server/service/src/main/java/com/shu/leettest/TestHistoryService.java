package com.shu.leettest;

import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.param.TestHistoryParam;
import com.shu.leettest.entity.Test;
import com.shu.leettest.entity.TestHistory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TestHistoryService {
    List<TestHistory> getProblemCollectionBySnameAndUserid(TestHistoryParam testHistoryParam);

    TestHistory submitChoice(TestHistoryParam testHistoryParam);

    TestHistory submitText(TestHistoryParam testHistoryParam);

    List<Test> queryTestByID(int userid);

    ResultVO getNumById(Integer userId);

    ResultVO getCorrectById(Integer userId);

    List<Map.Entry<LocalDate, Integer>> getDayNumById(Integer userId);

    List<Map.Entry<LocalDate, Integer>> getDayCorrectNumById(Integer userId);

    List<Map.Entry<String, Integer>> getPointNumById(Integer userId);
}
