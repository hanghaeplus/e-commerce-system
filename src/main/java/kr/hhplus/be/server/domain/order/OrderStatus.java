package kr.hhplus.be.server.domain.order;

import kr.hhplus.be.server.domain.common.CodeAware;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus implements CodeAware {

    /**
     * 결제대기
     */
    PAYMENT_WAITING(CodeAware.STEP),

    /**
     * 주문 완료
     */
    COMPLETED(2 * CodeAware.STEP),

    /**
     * 주문 취소
     */
    CANCELLED(3 * CodeAware.STEP),

    ;

    private final int code;

    public static OrderStatus from(int code) {
        for (OrderStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid code: " + code);
    }

}
