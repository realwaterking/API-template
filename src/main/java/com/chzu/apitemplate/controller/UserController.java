package com.chzu.apitemplate.controller;

import com.chzu.apitemplate.common.BaseResponse;
import com.chzu.apitemplate.common.ErrorCode;
import com.chzu.apitemplate.common.Result;
import com.chzu.apitemplate.exception.BusinessException;
import com.chzu.apitemplate.model.dto.user.UserDeleteRequest;
import com.chzu.apitemplate.model.dto.user.UserLoginRequset;
import com.chzu.apitemplate.model.dto.user.UserRegisterRequest;
import com.chzu.apitemplate.model.dto.user.UserUpdateRequest;
import com.chzu.apitemplate.model.entity.User;
import com.chzu.apitemplate.model.vo.LoginUserVO;
import com.chzu.apitemplate.model.vo.SelectUserVO;
import com.chzu.apitemplate.service.UserService;
import com.chzu.apitemplate.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;


    public UserController(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/login/")
    public BaseResponse<LoginUserVO> login(@RequestBody UserLoginRequset userLoginRequest) {

        System.out.println(userLoginRequest.getPhone() + userLoginRequest.getCode() + userLoginRequest.getToken() + "    Login");
        if (userLoginRequest == null) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR, "request login err");
        }
        if (StringUtils.isAnyBlank(userLoginRequest.getPhone(), userLoginRequest.getPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "");
        }
        BaseResponse<LoginUserVO> loginUserVOBaseResponse = userService.login(userLoginRequest);
        if (loginUserVOBaseResponse != null) {
            userLoginRequest.setToken(getLoginUserVOToken(userLoginRequest).getData());
            LoginUserVO loginUserVO = new LoginUserVO();
            BeanUtils.copyProperties(userLoginRequest, loginUserVO);
            return Result.success(loginUserVO);
        } else {
            return Result.error(ErrorCode.OPERATION_ERROR, "登录失败");
        }
    }

    @NotNull
    private BaseResponse<String> getLoginUserVOToken(@RequestBody UserLoginRequset userLoginRequest) {
        if (userLoginRequest != null) {
            JwtUtils jwtUtils = new JwtUtils();
            String token = jwtUtils.generateToken(userLoginRequest);
            userLoginRequest.setToken(token);
            redisTemplate.opsForValue().set("TOKEN_" + userLoginRequest.getPhone(), token, 1, TimeUnit.DAYS);
//            System.out.println(redisTemplate.opsForValue().get("TOKEN_" + userLoginRequset.getPhone()));
            return Result.success(token);
        } else {
            return Result.error(ErrorCode.PARAMS_ERROR, "缺少userLoginReeues");
        }

    }

//    @PostMapping("/login/wechat")
//    public String WeChatLogin(@RequestParam String phoneNumber, @RequestParam String code) {
//        User user = userService.login(phoneNumber, code);
////        String token = jwtUtils.generateToken(user);
//        return "token";
//    }

//
//    @GetMapping("/login/qq")
//    public BaseResponse<User> QQLogin(HttpServletRequest request, HttpServletResponse response,
//                                      @RequestParam("code") String code) {
////        if (StringUtils.isAnyBlank(phone, code)) {
////            throw new BusinessException(ErrorCode.PARAMS_ERROR);
////        }
//        User user = userService.wechatLogin("");
//        return Result.success(user);
//    }

    @PostMapping("/login/phone/")
    public BaseResponse<LoginUserVO> phoneLogin(@RequestBody UserLoginRequset userLoginRequest) {

        System.out.println(userLoginRequest.getPhone() + userLoginRequest.getCode() + "    phoneLogin");
        if (userLoginRequest == null) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR, "request phonelogin err");
        }

        if (StringUtils.isAnyBlank(userLoginRequest.getPhone(), userLoginRequest.getCode())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "手机号或验证码有blank");
        }
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userLoginRequest.setToken(getLoginUserVOToken(userLoginRequest).getData());
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(userLoginRequest, userRegisterRequest);
        BeanUtils.copyProperties(userLoginRequest, loginUserVO);
        userService.register(userRegisterRequest);
        return userService.login(userLoginRequest);
    }

    @GetMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    @GetMapping("/{id}")
    public BaseResponse<SelectUserVO> getUserById(@PathVariable Long id, @PathVariable String token){
        JwtUtils jwtUtils = new JwtUtils();
        if (jwtUtils.validateToken(token, redisTemplate)) {
            return userService.getUserById(id);
        } else {
            return Result.error(ErrorCode.NOT_LOGIN_ERROR, "请登录后再试");
        }
    }

    @PutMapping("/")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        JwtUtils jwtUtils = new JwtUtils();
       if (jwtUtils.validateToken(userUpdateRequest.getToken(), redisTemplate)) {
           User user = new User();
           BeanUtils.copyProperties(userUpdateRequest, user);
           System.out.println(user.getPhone() + user.getPassword() + "controller");
           return userService.updateUser(user);
       } else {
           return Result.error(ErrorCode.NOT_LOGIN_ERROR, "请登录后再试");
       }
    }

    @DeleteMapping("/")
    public BaseResponse<Boolean> deleteByPhone(@RequestBody UserDeleteRequest userDeleteRequest) {
        JwtUtils jwtUtils = new JwtUtils();
        if (jwtUtils.validateToken(userDeleteRequest.getToken(), redisTemplate)) {
            User user = new User();
            BeanUtils.copyProperties(userDeleteRequest, user);
            return userService.logicDelete(user);
        } else {
            return Result.error(ErrorCode.NOT_LOGIN_ERROR, "状态异常,删除失败");
        }


    }

    @GetMapping("/findAll")
    public List<User> findAll() {
        return userService.findAll();
    }
}
