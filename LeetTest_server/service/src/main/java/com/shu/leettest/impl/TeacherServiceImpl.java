package com.shu.leettest.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leettest.dao.SearchMapper;
import com.shu.leettest.dao.TeachersMapper;
import com.shu.leettest.entity.Search;
import com.shu.leettest.vo.ResStatus;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.TeacherVo;
import com.shu.leettest.vo.page.TeacherPageVo;
import com.shu.leettest.vo.param.TeacherPageParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.shu.leettest.TeacherService;
import com.shu.leettest.entity.Teachers;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeachersMapper teachersMapper;
    @Resource
    private SearchMapper searchMapper;

    @Override
    public ResultVO getTeacherInfoBySId(Integer sid, long currentPage) {
        Page<Teachers> page = new Page<>(currentPage, 5, true);
        QueryWrapper<Teachers> wrapper = new QueryWrapper<Teachers>().eq("schoolId", sid);
        teachersMapper.selectPage(page, wrapper);
        TeacherPageVo teacherPageVo = new TeacherPageVo();
        BeanUtils.copyProperties(page, teacherPageVo);

        List<TeacherVo> teacherVos = new ArrayList<>();
        for (Teachers record : page.getRecords()) {
            TeacherVo teacherVo = new TeacherVo(record.getId(), record.getTname(), record.getStudydir());
            teacherVos.add(teacherVo);
        }
        teacherPageVo.setTeacherVoList(teacherVos);
        return new ResultVO(ResStatus.OK, "老师分页信息查询成功", teacherPageVo);
    }

    @Override
    public ResultVO getTeacherInfoById(Integer id) {
        Teachers teacher = teachersMapper.selectById(id);
        return new ResultVO(ResStatus.OK, "老师详细信息查询成功", teacher);
    }

    @Override
    public ResultVO fuzzyQueryTeacher(TeacherPageParam teacherPageParam) {
        String text = teacherPageParam.getText();
        Integer uid = teacherPageParam.getUid();
        if (text == null)
            return new ResultVO(ResStatus.OK, "text为空", null);
        QueryWrapper<Teachers> wrapper = new QueryWrapper<>();
        wrapper.eq("schoolId", teacherPageParam.getSid())
                .and(wr -> {
                    wr.like("tname", text)
                            .or()
                            .like("studydir", text)
                            .or()
                            .like("eduresume", text)
                            .or()
                            .like("proresume", text)
                            .or()
                            .like("tURL", text);
                }).orderByAsc("id");
        IPage<Teachers> teachersPage = new Page<>();
        BeanUtils.copyProperties(teacherPageParam, teachersPage);
        teachersMapper.selectPage(teachersPage, wrapper);
        TeacherPageVo teacherPageVo = new TeacherPageVo();
        BeanUtils.copyProperties(teachersPage, teacherPageVo);
        ArrayList<TeacherVo> teacherVos = new ArrayList<>();
        for (Teachers record : teachersPage.getRecords()) {
            TeacherVo teacherVo = new TeacherVo();
            BeanUtils.copyProperties(record, teacherVo);
            teacherVos.add(teacherVo);
        }
        // 若查到数据，插入search表
        if (!teacherVos.isEmpty() && (uid != null))
            searchMapper.insert(new Search(0, uid, text));
        teacherPageVo.setTeacherVoList(teacherVos);
        return new ResultVO(ResStatus.OK, "教师模糊查询分页信息返回成功", teacherPageVo);
    }
}
