package cn.hb0730.spring.boot.seata.samples.integration.common.exception;

import cn.hb0730.spring.boot.seata.samples.integration.common.enums.RspStatusEnum;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class DefaultException extends RuntimeException{

    private RspStatusEnum rspStatusEnum;

    public DefaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public DefaultException(RspStatusEnum rspStatusEnum) {
        super(rspStatusEnum.getMessage());
        this.rspStatusEnum = rspStatusEnum;
    }

    public DefaultException(RspStatusEnum rspStatusEnum, Throwable cause) {
        super(rspStatusEnum.getMessage(), cause);
        this.rspStatusEnum = rspStatusEnum;
    }

    public RspStatusEnum getRspStatusEnum() {
        return rspStatusEnum;
    }

    public void setRspStatusEnum(RspStatusEnum rspStatusEnum) {
        this.rspStatusEnum = rspStatusEnum;
    }
}
