package com.shu.leettest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shu.leettest.EmailCodeService;
import com.shu.leettest.vo.ResultVO;

import javax.annotation.Resource;


@RestController
@RequestMapping("/ec")
public class EmailCodeController {
    @Resource
    private EmailCodeService emailCodeService;

    @GetMapping("/send/{email}")
    public ResultVO sendEmailCode(@PathVariable String email) {
        return emailCodeService.sendCode(email);
    }

    @GetMapping("/check/{code}/{eid}")
    public ResultVO checkCode(@PathVariable("code") String code, @PathVariable("eid") String eid) {
        return emailCodeService.checkCode(code, eid);
    }
}
