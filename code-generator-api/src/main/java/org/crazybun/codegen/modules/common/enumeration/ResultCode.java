package org.crazybun.codegen.modules.common.enumeration;


/**
 * <p> 响应码枚举，参考HTTP状态码的语义 </p>
 *
 * @author : CrazyBunQnQ
 * @description :
 * @date : 2019/8/22 11:09
 */
public enum ResultCode {
    /**
     * 响应成功
     */
    SUCCESS(200, "SUCCESS"),
    /**
     * 响应失败
     */
    FAILURE(4000, "FAILURE"),
    /**
     * 未认证或Token失效
     */
    UNAUTHORIZED(4001, "未认证或Token失效"),
    /**
     * 用户名或密码不正确
     */
    USER_UNAUTHORIZED(4002, "用户名或密码不正确"),
    /**
     * 接口不存在
     */
    NOT_FOUND(4004, "接口不存在"),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(5000, "服务器内部错误");

    private int code;
    private String desc;

    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
