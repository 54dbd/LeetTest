package com.shu.leettest.vo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestVo {
    /**
     * id
     */
    private Integer tid;

    /**
     * 题目名称
     */
    private String sname;

    /**
     * 难度
     */
    private String difficulty;

    /**
     * 正确率
     */
    private double accuracy;

    private String points;

    private String qtype;

    private String question;

    private Integer atype;

    private Integer num;

    private Integer correctnum;
}
