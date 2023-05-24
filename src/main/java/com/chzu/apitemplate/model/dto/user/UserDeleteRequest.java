package com.chzu.apitemplate.model.dto.user;

import lombok.Data;

/**
 * @author water king
 * @time 2023/5/24
 */
@Data
public class UserDeleteRequest {

    private static final long serialVersionUID = 3191241716373120793L;

    private String token;

    private String phone;
}
