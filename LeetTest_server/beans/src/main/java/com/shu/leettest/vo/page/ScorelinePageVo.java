package com.shu.leettest.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shu.leettest.vo.ScorelineVo;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScorelinePageVo extends Page{
    private List<ScorelineVo> scorelineVoList;
}
