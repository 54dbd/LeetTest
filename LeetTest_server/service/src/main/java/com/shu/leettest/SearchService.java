package com.shu.leettest;

import com.shu.leettest.vo.ResultVO;


public interface SearchService {
    void saveSearchByUIdAndText(Integer uid, String text);

    ResultVO getSearchHistoryByUId(Integer uid);
}
