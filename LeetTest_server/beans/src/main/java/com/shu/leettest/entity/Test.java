package com.shu.leettest.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("questions")
public class Test {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer tid;

    /**
     * 题目标题
     */
    private String sname;

    /**
     * 题目
     */
    private String question;

    /**
     * 答案
     */
    private String answer;

    /**
     * 正确率
     */
    private double accuracy;

    /**
     * 做题次数
     */
    private int num;

    /**
     * 正确次数
     */
    private int correctnum;

    /**
     * 知识点
     */
    private String points;

    /**
     * 难度
     */
    private String difficulty;

    /**
     * 正确解析
     */
    private String analysis;


    /**
     *选项A
     */
    private String qchoicea;

    /**
     *选项B
     */
    private String qchoiceb;

    /**
     *选项C
     */
    private String qchoicec;

    /**
     *选项D
     */
    private String qchoiced;

    /**
     * 答案类型
     * 0为简答题，1为选择题
     */
    private int atype;

    /**
     *题目类型（真题/模拟题）
     */
    private String qtype;

    public void setNum(int num){
        this.num = num;
    }


}