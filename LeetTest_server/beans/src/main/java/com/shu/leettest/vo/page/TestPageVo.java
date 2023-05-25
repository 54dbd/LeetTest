package com.shu.leettest.vo.page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shu.leettest.vo.TestVo;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestPageVo extends Page{
    List<TestVo> testVoList;
}
