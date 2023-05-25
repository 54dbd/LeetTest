package com.shu.leettest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionVo {
    private Boolean isLiked;
    private Boolean isFavorit;
    private Boolean isReposted;
}
