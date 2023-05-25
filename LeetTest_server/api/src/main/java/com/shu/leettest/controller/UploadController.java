package com.shu.leettest.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.shu.leettest.dao.MyFileMapper;
import com.shu.leettest.entity.MyFile;
import com.shu.leettest.utils.QiniuUtils;
import com.shu.leettest.vo.ResultVO;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Resource
    private QiniuUtils qiniuUtils;
    @Resource
    private MyFileMapper fileMapper;

    @PostMapping("/image")
    public ResultVO upload(@RequestParam("image")MultipartFile file) throws IOException {
        if(file == null) {
            return new ResultVO(400, "文件不能为空", null);
        }
        String fileName = UUID.randomUUID() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        // 上传到七牛云
        String upload = qiniuUtils.upload(file, fileName);
        if (upload != null){
            return new ResultVO(200, "成功", upload);
        }
        return new ResultVO(400, "失败", null);
    }

    @PostMapping("/uploadFile")
    public ResultVO uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        if(file == null) {
            return new ResultVO(400, "文件不能为空", null);
        }
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + "." + StringUtils.substringAfterLast(originalFilename, ".");
        // 上传到七牛云
        String upload = qiniuUtils.upload(file, fileName);
        MyFile myFile = new MyFile();
        myFile.setUrl(QiniuUtils.API_URL + fileName);
        myFile.setYear(getYear(originalFilename));
        fileMapper.insert(myFile);
        if (upload != null){
            return new ResultVO(200, "成功", upload);
        }
        return new ResultVO(400, "失败", null);
    }

    private Integer getYear(String name) {
        Integer[] years = new Integer[100];
        for (int i = 0; i < 100; i++) {
            years[i] = 1980 + i;
        }
        for (int i = 0; i < 100; i++) {
            if (name.contains(years[i].toString())) {
                return years[i];
            }
        }
        return 0;
    }
}
