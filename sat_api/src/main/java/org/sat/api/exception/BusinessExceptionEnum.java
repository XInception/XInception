package org.sat.api.exception;

import lombok.Getter;

@Getter
public enum BusinessExceptionEnum implements IExceptionEnum {
    /*未知异常*/
    GROUP_NOT_FOUND(10000, "客户信息不存在"),
    GROUP_EXIST(10000, "客户名称已经存在"),

    ACCOUNT_OR_PASSWORD_ERROR(10001, "用户名或者密码错误"),
    ACCOUNT_EXPIRED(10002, "账户已经到期请联系管理员"),
    ACCOUNT_EXIST(10003, "账户已经存在"),
    ACCOUNT_HAS_BEEN_LOGINED_IN_FROM_ELSEWHERE(10004, "账户已经从别处登录"),
    ACCOUNT_HAS_BEEN_LOGINED_IN_FROM_ELSEWHERE_1(10005, "账户已经从别处登录"),
    ACCOUNT_NOT_HAVE_LOGIN_PERMISSION(10007, "该账号没有登录权限"),
    ACCOUNT_MUST_NOT_NULL(10008, "账号不能为空"),
    ACCOUNT_NOT_FOUND(10009,"账户不存在"),

    PERMISSION_DENIED(20001, "访问被拒绝"),
    NEED_MIN_ONE_ROLE(20002, "需要最少一个用户角色"),


    SYSTEM_ERROR(9999, "系统错误"),
    /*页面不存在容错处理*/
    PAGE_NOT_FOUND(404, "api not found");


    BusinessExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;
}