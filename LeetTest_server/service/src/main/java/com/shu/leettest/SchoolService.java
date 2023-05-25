package com.shu.leettest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.SchoolNameVo;
import com.shu.leettest.vo.param.QueryPageParam;
import com.shu.leettest.entity.School;

import java.util.List;


public interface SchoolService {
    ResultVO getSchoolVoList(Page<School> pageParam);

    School getSchoolDetailBySid(Integer sid);

    List<SchoolNameVo> getSchoolNameList();

    ResultVO fuzzyQuerySchool(QueryPageParam queryPageParam);
}
