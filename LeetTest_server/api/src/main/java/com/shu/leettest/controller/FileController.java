package com.shu.leettest.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leettest.vo.param.MyFilePageParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shu.leettest.FileService;
import com.shu.leettest.entity.MyFile;
import com.shu.leettest.vo.ResultVO;

import javax.annotation.Resource;


@RestController
@RequestMapping("File")
public class FileController {
    @Resource
    FileService fileService;

    @PostMapping("getFiles")
    public ResultVO getFiles(@RequestBody MyFilePageParam myFilePageParam) {
        Integer code = myFilePageParam.getCode();

        String subject = null;
        if (code != null) {
            switch (code) {
                case 1:
                    subject = "政治";
                    break;
                case 2:
                    subject = "英语";
                    break;
                case 3:
                    subject = "数学";
                    break;
                case 4:
                    subject = "计算机";
                    break;
            }
        }
        Page<MyFile> page = myFilePageParam.getPage();
        return fileService.getFilePageBySubjectCode(subject, page);
    }
}
