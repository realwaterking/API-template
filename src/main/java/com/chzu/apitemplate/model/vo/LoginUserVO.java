package com.chzu.apitemplate.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author water king
 * @time 2023/5/19
 */
@Data
public class LoginUserVO implements Serializable {

    private Long id;

    private String username;

    private String phone;

    private String avatar;

    private String userProfile;

    private String userRole;

    private Date createTime;

    private Date updateTime;

    private String token;

    private static final long serialVersionUID = 1L;
}
