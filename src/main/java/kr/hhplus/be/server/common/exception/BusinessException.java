package kr.hhplus.be.server.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final BusinessError businessError;

    public BusinessException(BusinessError businessError) {
        super(businessError.getMessage());
        this.businessError = businessError;
    }

    public BusinessException(BusinessError businessError, Throwable cause) {
        super(businessError.getMessage(), cause);
        this.businessError = businessError;
    }

}
