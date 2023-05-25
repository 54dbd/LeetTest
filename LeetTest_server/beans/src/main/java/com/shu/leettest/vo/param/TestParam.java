package com.shu.leettest.vo.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestParam {
    private String sname;
    private Integer num;
    private Integer correctnum;
    private double accuracy;
    private String points;
}
