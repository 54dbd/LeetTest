package shu.java.csky;

import shu.java.csky.entity.TagName;
import shu.java.csky.vo.ResultVO;

import java.util.List;


public interface TagService {
    List<TagName> tagNameList();

    ResultVO addTagName(String tagName);
}
