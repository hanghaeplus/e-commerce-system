package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.domain.common.CodeAware;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiscountTarget implements CodeAware {

    /**
     * 정액 할인
     */
    FIXED_AMOUNT(CodeAware.STEP),

    /**
     * 정률 할인
     */
    FIXED_RATE(2 * CodeAware.STEP),

    /**
     * 배송비 할인
     */
    SHIPPING_FEE(3 * CodeAware.STEP),

    ;

    private final int code;

    public static DiscountTarget from(int code) {
        for (DiscountTarget target : values()) {
            if (target.getCode() == code) {
                return target;
            }
        }

        throw new IllegalArgumentException("Invalid code: " + code);
    }

}
