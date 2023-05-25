package com.shu.leettest.vo.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestHistoryParam {
    private String wronganswer;
    private Integer tid;
    private Integer userid;
    private String sname;
    private Integer score;
    private boolean iscorrect;
}
