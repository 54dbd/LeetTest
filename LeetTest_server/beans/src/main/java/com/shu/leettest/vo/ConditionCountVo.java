package com.shu.leettest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionCountVo {
    private Long likeCount;
    private Long favoriteCount;
    private Long repostCount;
}
