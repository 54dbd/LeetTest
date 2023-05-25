package com.shu.leettest;

import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.entity.TagName;

import java.util.List;


public interface TagService {
    List<TagName> tagNameList();

    ResultVO addTagName(String tagName);
}
