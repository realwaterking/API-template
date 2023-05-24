package com.chzu.apitemplate.model.dto.user;

import lombok.Data;

/**
 * @author water king
 * @time 2023/5/19
 */
@Data
public class UserRegisterRequest {

    private final long serialVersionUID = 3191241716373120793L;

    private String phone;

    private String password;

    private String token;

    private String code;

    private String userRole;
}
