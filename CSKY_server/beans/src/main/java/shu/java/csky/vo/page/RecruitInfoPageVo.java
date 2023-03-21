package shu.java.csky.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shu.java.csky.vo.RecruitInfoVo;

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
