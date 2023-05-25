package com.shu.leettest.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shu.leettest.vo.TeacherVo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherPageVo extends Page{
    List<TeacherVo> teacherVoList;
}
