package shu.java.csky.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import shu.java.csky.TestHistoryService;
import shu.java.csky.dao.TestHistoryMapper;
import shu.java.csky.entity.TestComment;
import shu.java.csky.entity.TestHistory;
import shu.java.csky.vo.ResStatus;
import shu.java.csky.vo.ResultVO;
import shu.java.csky.vo.param.TestHistoryParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 54dbd
 * @Classname TestHistoryServiceImpl
 * @date 2022/7/7 14:52
 */
@Service
public class TestHistoryServiceImpl implements TestHistoryService {

    @Resource
    TestHistoryMapper testHistoryMapper;
    @Override
    public List<TestHistory> getProblemCollectionBySnameAndUserid(TestHistoryParam testHistoryParam){
        Integer userid = testHistoryParam.getUserid();
        String sname = testHistoryParam.getSname();
        QueryWrapper<TestHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("sname",sname).eq("userid",userid);
        return testHistoryMapper.selectList(wrapper);
    }

    @Override
    public ResultVO insertHistory(TestHistoryParam testHistoryParam){
        TestHistory history = new TestHistory();
        BeanUtils.copyProperties(testHistoryParam, history);
        testHistoryMapper.insert(history);
        return new ResultVO(ResStatus.OK, "成功", history);
    }

    @Override
    public ResultVO getNumById(Integer userId){
        QueryWrapper<TestHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userId);
        System.out.println(testHistoryMapper.selectCount(wrapper));
        return new ResultVO(200,"获取做题数成功",testHistoryMapper.selectCount(wrapper)) ;
    }

    @Override
    public ResultVO getCorrectById(Integer userId){
        QueryWrapper<TestHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", userId).eq("iscorrect",true);
        System.out.println(testHistoryMapper.selectCount(wrapper));
        return new ResultVO(200,"获取正确题数成功",testHistoryMapper.selectCount(wrapper)) ;
    }
}
