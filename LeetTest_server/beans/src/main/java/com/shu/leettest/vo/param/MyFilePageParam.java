package com.shu.leettest.vo.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.shu.leettest.entity.MyFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFilePageParam {
    private Integer code;
    private Page<MyFile> page;
}
