package org.chens.app.exception;


import org.chens.core.enums.IBaseEnum;

/**
 * 基本错误枚举
 *
 * @author songchunlei@qq.com
 * @since 2018/3/8
 */
public enum AppExceptionEnum implements IBaseEnum {


    /**
     * 业务异常
     */
    NO_TABLE_NAME(802, "业务实体@TableName表名未定义"),
    NO_ROOT_ID(803, "根目录id未定义");

    private Integer code;

    private String message;

    AppExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }


}
