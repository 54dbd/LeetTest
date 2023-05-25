package com.shu.leettest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.entity.MyFile;


public interface FileService {
    ResultVO getFilePageBySubjectCode(String subject, IPage<MyFile> page);
}
