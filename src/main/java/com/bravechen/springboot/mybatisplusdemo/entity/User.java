package com.bravechen.springboot.mybatisplusdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author 陈庆勇
 * @since 2020-03-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user")
public class User extends DataEntity<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 用户手机
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 用户密码
     */
    @TableField("password")
    @JsonIgnore
    private String password;

    /**
     * 是否启用
     */
    @TableField("is_enabled")
    private Boolean enabled;

    /**
     * 是否锁定
     */
    @TableField("is_locked")
    private Boolean locked;

    /**
     * 是否过期
     */
    @TableField("is_expired")
    private Boolean expired;

    /**
     * 密码是否过期
     */
    @TableField("is_password_expired")
    private Boolean passwordExpired;

    /**
     * 是否删除
     */
    @TableField("is_deleted")
    private Boolean deleted;

    /**
     * 密码更新时间
     */
    @TableField("password_update_time")
    private LocalDateTime passwordUpdateTime;

    public static final String ID = "id";

    public static final String USERNAME = "username";

    public static final String NICKNAME = "nickname";

    public static final String MOBILE = "mobile";

    public static final String PASSWORD = "password";

    public static final String IS_ENABLED = "is_enabled";

    public static final String IS_LOCKED = "is_locked";

    public static final String IS_EXPIRED = "is_expired";

    public static final String IS_PASSWORD_EXPIRED = "is_password_expired";

    public static final String IS_DELETED = "is_deleted";

    public static final String PASSWORD_UPDATE_TIME = "password_update_time";


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
