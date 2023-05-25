package com.shu.leettest;

import com.shu.leettest.entity.User;
import com.shu.leettest.vo.ResultVO;

public interface UserService {

    //用户注册
    ResultVO userRegister(String name, String pwd, String email);

    //用户登录
    ResultVO checkLogin(String name, String pwd);

    User getUserById(String userId);


}
