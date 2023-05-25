package com.shu.leettest.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shu.leettest.vo.SchoolVo;

import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolPageVo extends Page{
    List<SchoolVo> schoolVoList;
}
