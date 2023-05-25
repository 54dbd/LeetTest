package com.shu.leettest.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shu.leettest.dao.MyFileMapper;
import com.shu.leettest.vo.ResStatus;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.page.MyFilePageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.shu.leettest.FileService;
import com.shu.leettest.entity.MyFile;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    MyFileMapper myFileMapper;

    @Override
    public ResultVO getFilePageBySubjectCode(String subject, IPage<MyFile> page) {
        QueryWrapper<MyFile> wrapper = new QueryWrapper<>();
        wrapper.like("subject", subject)
                .orderByDesc("year")
                .orderByAsc("subject_code");
        myFileMapper.selectPage(page, wrapper);
        List<MyFile> records = page.getRecords();
        MyFilePageVo myFilePageVo = new MyFilePageVo();
        BeanUtils.copyProperties(page, myFilePageVo);
        myFilePageVo.setFileList(records);
        return new ResultVO(ResStatus.OK, "试题查询成功", myFilePageVo);
    }
}
