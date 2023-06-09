package com.shu.leettest.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shu.leettest.dao.EmailCodeMapper;
import com.shu.leettest.utils.mailUtil.MailUtil;
import com.shu.leettest.vo.EmailCodeVo;
import com.shu.leettest.vo.ResStatus;
import com.shu.leettest.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.shu.leettest.EmailCodeService;
import com.shu.leettest.entity.EmailCode;

import javax.annotation.Resource;
import java.util.Random;


@Service
public class EmailCodeServiceImpl implements EmailCodeService {
    @Resource
    private EmailCodeMapper emailCodeMapper;

    /**
     * 生成6位数验证码
<<<<<<< HEAD
=======
     *
>>>>>>> 8444f5c (增加vue-particles特效，修复邮箱注册功能)
     * @return 返回验证码
     */
    private String createCode() {
        char[] code = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2',
                '3', '4', '5', '6', '7', '8', '9'};
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(code[random.nextInt(code.length)]);
        }
        return sb.toString();
    }

    /**
     * 1. 已经注册过的不能再注册
     * 2. 验证码的时效问题
     *
     * @param email 邮箱
     * @return  返回发送结果
     */
    @Override
    public ResultVO sendCode(String email) {
        EmailCode emailCode = new EmailCode();
        emailCode.setCode(createCode());
        emailCode.setEmail(email);
        try {
            MailUtil.send_mail(email, emailCode.getCode());
        } catch (Exception e) {
            throw new RuntimeException("邮箱验证码发送失败！");
        }
        try {
            emailCodeMapper.insert(emailCode);
            QueryWrapper<EmailCode> wrapper = new QueryWrapper<>();
            wrapper.eq("code", emailCode.getCode());
            EmailCode emailCode1 = emailCodeMapper.selectOne(wrapper);
            EmailCodeVo emailCodeVo = new EmailCodeVo();
            BeanUtils.copyProperties(emailCode1, emailCodeVo);
            return new ResultVO(ResStatus.OK, "验证码发送成功！", emailCodeVo);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(ResStatus.NO, "验证码发送失败！", null);
        }
    }

    @Override
    public ResultVO checkCode(String code, String eid) {
        EmailCode emailCode = emailCodeMapper.selectById(eid);
        if (emailCode.getCode().equals(code)) {
            return new ResultVO(ResStatus.OK, "验证码正确！", null);
        }
        return new ResultVO(ResStatus.NO, "验证码错误！", null);
    }
}
