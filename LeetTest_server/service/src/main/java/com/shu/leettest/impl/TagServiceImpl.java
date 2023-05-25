package com.shu.leettest.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shu.leettest.dao.TagNameMapper;
import com.shu.leettest.dao.TagsMapper;
import com.shu.leettest.vo.ResStatus;
import com.shu.leettest.vo.ResultVO;
import org.springframework.stereotype.Service;
import com.shu.leettest.TagService;
import com.shu.leettest.entity.TagName;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagsMapper tagsMapper;
    @Resource
    private TagNameMapper tagNameMapper;

    @Override
    public List<TagName> tagNameList() {
        return tagNameMapper.selectList(null);
    }

    @Override
    public ResultVO addTagName(String tagName) {
        QueryWrapper<TagName> tagNameQueryWrapper = new QueryWrapper<>();
        tagNameQueryWrapper.eq("tag_name", tagName);
        Long count = tagNameMapper.selectCount(tagNameQueryWrapper);
        if (count > 0) {
            return new ResultVO(ResStatus.NO, "改标签已存在", null);
        }
        TagName tagName1 = new TagName();
        tagName1.setTagName(tagName);
        tagNameMapper.insert(tagName1);
        return new ResultVO(ResStatus.OK, "添加成功", tagName1);
    }
}
