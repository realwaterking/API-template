package com.chzu.apitemplate.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chzu.apitemplate.common.BaseResponse;
import com.chzu.apitemplate.common.ErrorCode;
import com.chzu.apitemplate.common.Result;
import com.chzu.apitemplate.exception.BusinessException;
import com.chzu.apitemplate.mapper.UserMapper;
import com.chzu.apitemplate.model.dto.user.UserLoginRequset;
import com.chzu.apitemplate.model.dto.user.UserRegisterRequest;
import com.chzu.apitemplate.model.entity.User;
import com.chzu.apitemplate.model.vo.LoginUserVO;
import com.chzu.apitemplate.model.vo.SelectUserVO;
import com.chzu.apitemplate.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.chzu.apitemplate.constant.UserConstant.USER_LOGIN_STATE;

@Service
@Slf4j
public  class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "water";
    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserMapper userMapper;

    @Override
    public BaseResponse<SelectUserVO> getUserById(Long id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id" , id);
        return getSelectUserVO(userMapper.selectOne(queryWrapper));
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        Object UserObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) UserObj;

        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        long userId =currentUser.getId();
        currentUser = userMapper.selectById(userId);

        if (currentUser == null) {
            throw  new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        return currentUser;
    }

    @Override
    public BaseResponse<Boolean> updateUser(User user) {
        QueryWrapper<UserRegisterRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone());

        if (StringUtils.isAnyBlank(user.getPassword(), user.getPhone())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (user.getIsDelete() == 1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "该账户已删除,操作失败");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + user.getPassword()).getBytes());
        if (userMapper.getByPhoneNumber(user.getPhone()) != null) {
            userMapper.update(encryptPassword, user.getPhone());
            return Result.success(true);
        }
        else return Result.error(ErrorCode.PARAMS_ERROR, "更新失败");
    }

    @Override
    public BaseResponse<Boolean> logicDelete(User user) {
        if (userMapper.logicDelete(user.getPhone())) {
            return Result.success(true);
        } else return Result.error(ErrorCode.PARAMS_ERROR, "user.id < 0 or other");
    }

    @Override
    public BaseResponse<Boolean> logout(HttpServletRequest request) {

        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
                throw  new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        //移除登录状态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return Result.success(true);
    }

    @Override
    public BaseResponse<LoginUserVO> register(UserRegisterRequest userRegisterRequest) {

        String value = (String) redisTemplate.opsForValue().get(userRegisterRequest.getPhone());
        System.out.println("redis cache checkword" + value);
        System.out.println(value.equals(userRegisterRequest.getCode()));
        if (value == null || !value.equals(userRegisterRequest.getCode())) {
            throw new IllegalArgumentException("验证码不正确");
        }

        User user = new User();
        BeanUtils.copyProperties(userRegisterRequest, user);

        if (isDelete(user) || StringUtils.isEmpty(user.getPhone())) {
            userMapper.insert(user);
            return Result.success(getLoginUserVO(user));
        } else {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone", user.getPhone());
            return Result.success(getLoginUserVO(userMapper.selectOne(queryWrapper)));

        }
    }

    @Override
    public BaseResponse<LoginUserVO> login(UserLoginRequset userLoginRequset) {

        if (StringUtils.isAnyBlank( userLoginRequset.getPhone())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 2. 加密
//        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userLoginRequset.getPassword()).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", userLoginRequset.getPhone()).ne("is_delete", 1);
        User user = this.userMapper.selectOne(queryWrapper);

        if(user == null) {
            log.info("user login failed, phone cannot match password");
            throw  new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误或用户不存在");
        }
        BeanUtils.copyProperties(userLoginRequset, user);
        // 登录成功
        return Result.success(getLoginUserVO(user));
    }

    private LoginUserVO getLoginUserVO(User user) {

        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    private boolean isDelete(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", user.getPhone());
        User isUser = userMapper.selectOne(queryWrapper);
        if (isUser == null || isUser.getIsDelete() == 1) {
            return true;
        } else return false;
    }

    private BaseResponse<SelectUserVO> getSelectUserVO(User user) {
        if (user == null) {
            return null;
        }
        SelectUserVO selectUserVO = new SelectUserVO();
        BeanUtils.copyProperties(user, selectUserVO);
        return Result.success(selectUserVO);
    }


    @Override
    public List<User> findAll() {
        return userMapper.selectList(null);
    }

    @Override
    public User wechatLogin(String openid) {
        return null;
    }

    @Override
    public User getByOpenid(String openid) {
        return null;
    }

}
