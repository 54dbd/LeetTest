package com.shu.leettest.vo.param;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionParam {
    private Integer aid;
    private Integer uid;
}
