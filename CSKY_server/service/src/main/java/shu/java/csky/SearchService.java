package shu.java.csky;

import shu.java.csky.vo.ResultVO;


public interface SearchService {
    void saveSearchByUIdAndText(Integer uid, String text);

    ResultVO getSearchHistoryByUId(Integer uid);
}
