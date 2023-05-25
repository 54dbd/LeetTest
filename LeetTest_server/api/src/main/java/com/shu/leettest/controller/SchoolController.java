package com.shu.leettest.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shu.leettest.vo.param.QueryPageParam;
import org.springframework.web.bind.annotation.*;
import com.shu.leettest.SchoolService;
import com.shu.leettest.entity.School;
import com.shu.leettest.vo.ResStatus;
import com.shu.leettest.vo.ResultVO;

import javax.annotation.Resource;


@RestController
@RequestMapping("school")
public class SchoolController {
    @Resource
    private SchoolService schoolService;

    @PostMapping("/list")
    public ResultVO schoolList(@RequestBody Page<School> pageParam) {
        if (pageParam == null) {
            return new ResultVO(400, "参数不能为空~", null);
        }
        return schoolService.getSchoolVoList(pageParam);
    }

    @GetMapping("/detail/{sid}")
    public ResultVO getSchoolDetail(@PathVariable Integer sid) {
        if (sid == null || sid == 0) {
            return new ResultVO(400, "参数不能为空~", null);
        }
        School school = schoolService.getSchoolDetailBySid(sid);
        return new ResultVO(200, "成功", school);
    }

    @GetMapping("/schoolNameList")
    public ResultVO getSchoolNameList() {
        return new ResultVO(ResStatus.OK, "成功", schoolService.getSchoolNameList());
    }


    @PostMapping("/query")
    public ResultVO fuzzyQuerySchool(@RequestBody QueryPageParam queryPageParam) {
        return schoolService.fuzzyQuerySchool(queryPageParam);
    }
}
