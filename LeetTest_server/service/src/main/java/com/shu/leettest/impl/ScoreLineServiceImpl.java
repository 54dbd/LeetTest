package com.shu.leettest.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leettest.dao.SchoolMapper;
import com.shu.leettest.dao.ScorelineMapper;
import com.shu.leettest.vo.ResStatus;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.ScorelineVo;
import com.shu.leettest.vo.page.ScorelinePageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.shu.leettest.ScoreLineService;
import com.shu.leettest.entity.School;
import com.shu.leettest.entity.Scoreline;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class ScoreLineServiceImpl implements ScoreLineService {
    @Resource
    ScorelineMapper scorelineMapper;
    @Resource
    SchoolMapper schoolMapper;

    @Override
    public ResultVO getScorelineBySid(Integer sid) {
        QueryWrapper<Scoreline> wrapper = new QueryWrapper<>();
        wrapper.eq("school_id", sid)
                .orderByDesc("score_21")
                .orderByDesc("score_20")
                .orderByDesc("score_19");
        String sname = schoolMapper.selectById(sid).getSname();
        List<Scoreline> scorelines = scorelineMapper.selectList(wrapper);
        ArrayList<ScorelineVo> scorelineVos = new ArrayList<>();
        for (Scoreline scoreline : scorelines) {
            ScorelineVo scorelineVo = new ScorelineVo();
            BeanUtils.copyProperties(scoreline, scorelineVo);
            scorelineVo.setSname(sname);
            scorelineVos.add(scorelineVo);
        }
        return new ResultVO(ResStatus.OK, "由sid查询学校分数线成功", scorelineVos);
    }

    @Override
    public ResultVO getScorelinePage(Page<Scoreline> scorelineIPage) {
        QueryWrapper<Scoreline> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("score_21")
                .orderByDesc("score_20")
                .orderByDesc("score_19");
        scorelineMapper.selectPage(scorelineIPage, wrapper);
        ScorelinePageVo scorelinePageVo = new ScorelinePageVo();
        BeanUtils.copyProperties(scorelineIPage, scorelinePageVo);
        List<Scoreline> records = scorelineIPage.getRecords();
        ArrayList<ScorelineVo> scorelineVos = new ArrayList<>();
        for (Scoreline record : records) {
            ScorelineVo scorelineVo = new ScorelineVo();
            BeanUtils.copyProperties(record, scorelineVo);
            School school = schoolMapper.selectById(record.getSchoolId());
            scorelineVo.setSname(school.getSname());
            scorelineVos.add(scorelineVo);
        }
        scorelinePageVo.setScorelineVoList(scorelineVos);
        return new ResultVO(ResStatus.OK, "分数线分页信息查询成功", scorelinePageVo);
    }


}
