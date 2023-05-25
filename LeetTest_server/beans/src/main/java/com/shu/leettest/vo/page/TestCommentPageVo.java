package com.shu.leettest.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shu.leettest.vo.TestCommentVo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCommentPageVo extends Page{
    List<TestCommentVo> testCommentVoList;
}
