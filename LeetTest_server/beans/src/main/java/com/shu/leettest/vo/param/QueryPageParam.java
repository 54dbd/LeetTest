package com.shu.leettest.vo.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryPageParam {
    private String text;

    private Integer uid;
    /**
     * 当前页
     */
    private long current;
    /**
     * 总页数
     */
    private long pages;
    /**
     * 每页条数
     */
    private long size;
    /**
     * 总记录数
     */
    private long total;
}
