package com.shu.leettest;

import com.shu.leettest.vo.ResultVO;


public interface EmailCodeService {
    /**
     * 给指定邮箱发送验证码
     * @param email
     * @return
     */
    ResultVO sendCode(String email);

    /**
     * 校验验证码是否正确
     * @param code
     * @return
     */
    ResultVO checkCode(String code, String eid);
}
