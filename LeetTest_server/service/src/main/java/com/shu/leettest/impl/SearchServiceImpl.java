package com.shu.leettest.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shu.leettest.dao.SearchMapper;
import com.shu.leettest.entity.Search;
import com.shu.leettest.vo.ResStatus;
import com.shu.leettest.vo.ResultVO;
import org.springframework.stereotype.Service;
import com.shu.leettest.SearchService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class SearchServiceImpl implements SearchService {
    @Resource
    SearchMapper searchMapper;

    @Override
    public void saveSearchByUIdAndText(Integer uid, String text) {
        Search search = new Search(0, uid, text);
        searchMapper.insert(search);
    }

    @Override
    public ResultVO getSearchHistoryByUId(Integer uid) {
        QueryWrapper<Search> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid).orderByAsc("id").last("LIMIT 10");
        List<Search> searches = searchMapper.selectList(wrapper);
        ArrayList<String> textList = new ArrayList<>();
        for (Search search : searches) {
            textList.add(search.getText());
        }
        return new ResultVO(ResStatus.OK, "搜索历史返回成功", textList);
    }
}
