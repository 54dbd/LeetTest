package com.shu.leettest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.TestNameVo;
import com.shu.leettest.vo.param.QueryPageParam;
import com.shu.leettest.vo.param.TestParam;
import org.springframework.stereotype.Service;
import com.shu.leettest.entity.Test;

import java.util.List;


@Service
public interface TestService {

    ResultVO getTestVoList(Page<Test> pageParam);

    Test getTestDetailByTid(Integer tid);

    List<TestNameVo> getTestNameList();

    ResultVO fuzzyQueryTest(QueryPageParam queryPageParam);

    ResultVO setNumByAid(TestParam testParam);


}

