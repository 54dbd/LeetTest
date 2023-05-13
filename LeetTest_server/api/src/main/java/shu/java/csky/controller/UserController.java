package shu.java.csky.controller;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import shu.java.csky.ArticleService;
import shu.java.csky.TestHistoryService;
import shu.java.csky.UserService;
import shu.java.csky.annotation.UserLoginToken;
import shu.java.csky.entity.User;
import shu.java.csky.vo.ResStatus;
import shu.java.csky.vo.ResultVO;
import shu.java.csky.vo.UserVo;
import shu.java.csky.vo.param.IdParam;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private ArticleService articleService;

    @Resource
    private TestHistoryService testHistoryService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public ResultVO login(@RequestBody User user) {
        ResultVO resultVO = userService.checkLogin(user.getUsername(), user.getPassword());
        logger.info(resultVO.getMsg());
        return resultVO;
    }

    @PostMapping("/register")
    public ResultVO register(@RequestBody User user) {
        ResultVO resultVO = userService.userRegister(user.getUsername(), user.getPassword(), user.getUserEmail());
        logger.info(resultVO.getMsg());
        return resultVO;
    }

    @UserLoginToken
    @PostMapping("/userInfo")
    public ResultVO getUserInfo(@RequestHeader("TOKEN") String token) {
        if (StringUtils.isBlank(token)) {
            return new ResultVO(ResStatus.NO, "TOKEN信息为空~", null);
        }
        UserVo userVo;
        try {
            userVo = JSON.parseObject(JWT.decode(token).getAudience().get(0), UserVo.class);
        } catch (Exception e) {
            return new ResultVO(ResStatus.NO, "TOKEN有误，休想骗我~", null);
        }
        return new ResultVO(ResStatus.OK, "获取成功！", userVo);
    }

    @PostMapping("/favorite")
    public ResultVO getFavoriteByUId(@RequestBody IdParam idParam) {
        return articleService.getFavoriteByUId(idParam);
    }

    @PostMapping("/author")
    public ResultVO getWorkByUId(@RequestBody IdParam idParam) {
        return articleService.getWorkByUId(idParam);
    }

    @GetMapping("/num/{userid}")
    public ResultVO getNumById(@PathVariable Integer userid){
        return new ResultVO(200,"获取做题数成功",testHistoryService.getNumById(userid)) ;
    }

    @GetMapping("/correct/{userid}")
    public ResultVO getCorrectById(@PathVariable Integer userid){
        return new ResultVO(200,"获取正确题数成功",testHistoryService.getCorrectById(userid));
    }
    @GetMapping("/dayNum/{userid}")
    public ResultVO getDayNumById(@PathVariable Integer userid){
        return new ResultVO(200,"获取每日做题数量成功",testHistoryService.getDayNumById(userid));
    }
    @GetMapping("/dayCorrectNum/{userid}")
    public ResultVO getDayCorrectNumById(@PathVariable Integer userid){
        return new ResultVO(200,"获取每日做题正确数量成功",testHistoryService.getDayCorrectNumById(userid));
    }
}
