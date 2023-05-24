package com.chzu.apitemplate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chzu.apitemplate.common.BaseResponse;
import com.chzu.apitemplate.model.dto.user.UserLoginRequset;
import com.chzu.apitemplate.model.dto.user.UserRegisterRequest;
import com.chzu.apitemplate.model.entity.User;
import com.chzu.apitemplate.model.vo.LoginUserVO;
import com.chzu.apitemplate.model.vo.SelectUserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService extends IService<User> {

    BaseResponse<SelectUserVO> getUserById(Long id);

    User getLoginUser(HttpServletRequest request);

    BaseResponse<Boolean> updateUser(User user);

    BaseResponse<Boolean> logicDelete(User user);

    BaseResponse<Boolean> logout(HttpServletRequest request);

    BaseResponse<LoginUserVO> register(UserRegisterRequest userRegisterRequest);

    BaseResponse<LoginUserVO> login(UserLoginRequset userLoginRequset);

    List<User> findAll();

    User wechatLogin(String openid);

    User getByOpenid(String openid);


}
