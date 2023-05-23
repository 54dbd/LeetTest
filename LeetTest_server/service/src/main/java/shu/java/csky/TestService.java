package shu.java.csky;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import shu.java.csky.entity.Test;
import shu.java.csky.vo.ResultVO;
import shu.java.csky.vo.TestNameVo;
import shu.java.csky.vo.param.QueryPageParam;
import shu.java.csky.vo.param.TestParam;

import java.io.UnsupportedEncodingException;
import java.util.List;


@Service
public interface TestService {

    ResultVO getTestVoList(Page<Test> pageParam);

    Test getTestDetailByTid(Integer tid);

    List<TestNameVo> getTestNameList();

    ResultVO fuzzyQueryTest(QueryPageParam queryPageParam);

    ResultVO setNumByAid(TestParam testParam);


}

