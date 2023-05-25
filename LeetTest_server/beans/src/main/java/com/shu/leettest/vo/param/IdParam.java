package com.shu.leettest.vo.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdParam {
    private Integer aid;
    private Integer uid;
    private Integer cid;
    private Long current;
    private Long size;
    private String userid;
}
