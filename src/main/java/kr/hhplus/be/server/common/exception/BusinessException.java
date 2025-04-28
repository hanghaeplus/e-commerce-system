package kr.hhplus.be.server.common.exception;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public class BusinessException extends RuntimeException {

    private final BusinessError businessError;

    @Nullable
    private final Object data;

    public BusinessException(BusinessError businessError) {
        this(businessError, null);
    }

    public BusinessException(BusinessError businessError, @Nullable Object data) {
        super(businessError.getMessage());
        this.businessError = businessError;
        this.data = data;
    }

}
