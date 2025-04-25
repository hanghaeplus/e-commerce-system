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
    PAYMENT_WAITING(100),

    /**
     * 주문 완료
     */
    COMPLETED(PAYMENT_WAITING.code + CodeAware.STEP),

    /**
     * 주문 취소
     */
    CANCELLED(COMPLETED.code + CodeAware.STEP),

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
