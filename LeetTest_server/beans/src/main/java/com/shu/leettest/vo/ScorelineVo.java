package com.shu.leettest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScorelineVo {
    private String sname;

    private Integer id;

    private Integer schoolId;

    private String collegeName;

    private String majorCode;

    private String majorName;

    private String majorDir;

    private Integer score21;

    private Integer score20;

    private Integer score19;

    private String englishSubject;

    private String mathSubject;

    private String politicsSubject;

    private String computerSubject;
}
