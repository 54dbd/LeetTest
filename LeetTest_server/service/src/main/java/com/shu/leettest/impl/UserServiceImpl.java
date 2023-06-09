package com.shu.leettest.impl;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shu.leettest.dao.TestHistoryMapper;
import com.shu.leettest.dao.UserMapper;
import com.shu.leettest.entity.User;
import com.shu.leettest.utils.MD5Utils;
import com.shu.leettest.vo.ResStatus;
import com.shu.leettest.vo.ResultVO;
import com.shu.leettest.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.shu.leettest.UserService;
import com.shu.leettest.dao.AvatarMapper;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private AvatarMapper avatarMapper;

    @Resource
    private TestHistoryMapper testHistoryMapper;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private boolean isRegister(String name) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", name);
        List<User> userList = userMapper.selectList(userQueryWrapper);
        return userList != null && userList.size() != 0;
    }

    @Override
    public ResultVO userRegister(String name, String pwd, String email) {
        //1.根据用户查询，这个用户是否已经被注册
        if (isRegister(name)) {
            return new ResultVO(ResStatus.NO, "用户名已经被注册！", null);
        }

        //2.如果没有被注册则进行保存操作
        String md5Pwd = MD5Utils.md5(pwd);
        String md5Email = MD5Utils.md5(email);
        User u = new User();
        u.setUsername(name);
        u.setPassword(md5Pwd);
        //3. 设置Gravatar头像
        u.setUserImg("https://www.gravatar.com/avatar/" + md5Email + "?s=200&d=identicon&r=PG");
        u.setUserEmail(email);
        u.setUserRegtime(new Date());
        u.setUserModtime(new Date());

        // 执行保存操作
        int count = userMapper.insert(u);
        if (count == 1) {
            User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", u.getUsername()));
            user.setPassword(null);
            return new ResultVO(ResStatus.OK, "注册成功！", user);
        }
        return new ResultVO(ResStatus.NO, "注册失败！", null);
    }

    @Override
    public ResultVO checkLogin(String name, String pwd) {
        // 获取该用户名对应的user对象
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", name);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null) {
            return new ResultVO(ResStatus.NO, "用户名或密码错误！", null);
        }
        // 得到该用户信息后
        String md5Pwd = MD5Utils.md5(pwd);
        if (md5Pwd.equals(user.getPassword())) { // 密码正确
            //如果登录验证成功且已经激活，则需要生成令牌token（token就是按照特定规则生成的字符串）
            HashMap<String, Object> map = new HashMap<>();
            map.put("TOKEN", createToken(user));
            return new ResultVO(ResStatus.OK, "登录成功！", map);
        }
        throw new RuntimeException("用户名或密码错误！");
    }

    @Override
    public User getUserById(String userId) {
        return userMapper.selectById(userId);
    }

    private String createToken(User user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        Algorithm algorithm = Algorithm.HMAC256(user.getPassword());

        Date nowDate = new Date();
        Date expireDate = new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000);

        return JWT.create()
                .withIssuedAt(nowDate) //设置 载荷 生成签名的时间
                .withExpiresAt(expireDate)//设置 载荷 签名过期的时间
                .withAudience(JSON.toJSONString(userVo), user.getUsername())
                .sign(algorithm);//签名 Signature
    }


}
