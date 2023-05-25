package com.shu.leettest;

import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.param.TeacherPageParam;


public interface TeacherService {
    /**
     * 根据学校sid获取教师信息
     *
     * @param sid
     * @return
     */
    ResultVO getTeacherInfoBySId(Integer sid, long currentPage);

    ResultVO getTeacherInfoById(Integer id);

    ResultVO fuzzyQueryTeacher(TeacherPageParam teacherPageParam);
}
