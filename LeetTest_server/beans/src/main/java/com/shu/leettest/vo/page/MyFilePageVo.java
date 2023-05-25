package com.shu.leettest.vo.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shu.leettest.entity.MyFile;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFilePageVo extends Page {
    private List<MyFile> fileList;
}
