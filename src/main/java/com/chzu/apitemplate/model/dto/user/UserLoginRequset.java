package com.chzu.apitemplate.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author water king
 * @time 2023/5/19
 */
@Data
public class UserLoginRequset implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String phone;

    private String password;

    private String code;

    private String token;
}
