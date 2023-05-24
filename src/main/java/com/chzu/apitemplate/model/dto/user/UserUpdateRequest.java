package com.chzu.apitemplate.model.dto.user;

import lombok.Data;

/**
 * @author water king
 * @time 2023/5/24
 */
@Data
public class UserUpdateRequest {

    private final long serialVersionUID = 3191241716373120792L;

    private String phone;

    private String password;

    private String token;

    private String userRole;
}
