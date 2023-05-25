package com.shu.leettest.vo.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleBriefParam {
    private Integer id;
    private String title;
    private String content;
    private String authorName;
    private Long likeCount;
    private Long commentCount;
    private Date createTime;
}


