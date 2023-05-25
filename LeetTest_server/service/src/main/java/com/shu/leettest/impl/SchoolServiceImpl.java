package com.shu.leettest.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leettest.dao.SchoolMapper;
import com.shu.leettest.entity.Search;
import com.shu.leettest.vo.ResStatus;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.SchoolNameVo;
import com.shu.leettest.vo.SchoolVo;
import com.shu.leettest.vo.page.SchoolPageVo;
import com.shu.leettest.vo.param.QueryPageParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.shu.leettest.SchoolService;
import com.shu.leettest.dao.SearchMapper;
import com.shu.leettest.entity.School;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class SchoolServiceImpl implements SchoolService {
    @Resource
    private SchoolMapper schoolMapper;
    @Resource
    private SearchMapper searchMapper;

    @Override
    public ResultVO getSchoolVoList(Page<School> pageParam) {
        QueryWrapper<School> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sid");
        schoolMapper.selectPage(pageParam, wrapper);
        ArrayList<SchoolVo> schoolVos = new ArrayList<>();
        for (School record : pageParam.getRecords()) {
            SchoolVo schoolVo = new SchoolVo(record.getSid(), record.getSname(), record.getShortIntroduction(), record.getBadgeImg());
            schoolVos.add(schoolVo);
        }
        SchoolPageVo schoolPageVo = new SchoolPageVo();
        schoolPageVo.setSchoolVoList(schoolVos);
        BeanUtils.copyProperties(pageParam, schoolPageVo);
        return new ResultVO(ResStatus.OK, "学校分页信息查询成功", schoolPageVo);
    }

    @Override
    public School getSchoolDetailBySid(Integer sid) {
        return schoolMapper.selectById(sid);
    }

    @Override
    public List<SchoolNameVo> getSchoolNameList() {
        List<School> schoolList = schoolMapper.selectList(null);
        List<SchoolNameVo> schoolNameVoList = new ArrayList<>();
        for (School school : schoolList) {
            SchoolNameVo schoolNameVo = new SchoolNameVo();
            BeanUtils.copyProperties(school, schoolNameVo);
            schoolNameVoList.add(schoolNameVo);
        }
        return schoolNameVoList;
    }

    @Override
    public ResultVO fuzzyQuerySchool(QueryPageParam queryPageParam) {
        String text = queryPageParam.getText();
        Integer uid = queryPageParam.getUid();
        // 若text为空，直接返回
        if (text == null)
            return new ResultVO(ResStatus.OK, "text为空", null);

        QueryWrapper<School> wrapper = new QueryWrapper<>();
        wrapper.like("sname", text)
                .or()
                .like("introduction", text)
                .or()
                .like("short_introduction", text)
                .or()
                .like("official_Site", text)
                .orderByAsc("sid");
        IPage<School> schoolPage = new Page<>();
        BeanUtils.copyProperties(queryPageParam, schoolPage);
        schoolMapper.selectPage(schoolPage, wrapper);
        SchoolPageVo schoolPageVo = new SchoolPageVo();
        BeanUtils.copyProperties(schoolPage, schoolPageVo);
        ArrayList<SchoolVo> schoolVos = new ArrayList<>();
        for (School record : schoolPage.getRecords()) {
            SchoolVo schoolVo = new SchoolVo();
            BeanUtils.copyProperties(record, schoolVo);
            schoolVos.add(schoolVo);
        }
        // 若查到数据，插入search表
        if (!schoolVos.isEmpty() && (uid != null))
            searchMapper.insert(new Search(0, uid, text));
        schoolPageVo.setSchoolVoList(schoolVos);
        return new ResultVO(ResStatus.OK, "学校模糊查询分页信息返回成功", schoolPageVo);
    }

}
