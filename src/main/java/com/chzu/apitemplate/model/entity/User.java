package com.chzu.apitemplate.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user")
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 微信公众平台openid
     */
    private String wechatOpenid;

    /**
     * QQ互联平台openid
     */
    private String qqOpenid;

    /**
     * github平台openid
     */
    private String githubOpenid;

    /**
     * 微博开放平台openid
     */
    private String weiboOpenid;

    /**
     * nickName
     */
    private String username;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 开放平台id
     */
    private String unionId;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 用于传输token，逻辑字段
     */
    @TableField(exist = false)
    private String token;

    /**
     * 是否逻辑删除
     */
    @TableLogic
    private Integer isDelete = 0;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
