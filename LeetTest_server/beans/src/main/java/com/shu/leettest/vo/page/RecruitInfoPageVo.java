package com.shu.leettest.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shu.leettest.vo.RecruitInfoVo;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruitInfoPageVo extends Page {
    /**
     * 评论数
     */
    private long commentCount;
    /**
     * 评论
     */
    private List<RecruitInfoVo> recruitInfoVoList;
}
