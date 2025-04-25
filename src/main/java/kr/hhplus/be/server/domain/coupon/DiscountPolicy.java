package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.domain.common.CodeAware;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiscountPolicy implements CodeAware {

    /**
     * 정액 할인
     */
    FIXED_AMOUNT(100),

    /**
     * 정률 할인
     */
    FIXED_RATE(FIXED_AMOUNT.code + CodeAware.STEP),

    /**
     * 배송비 할인
     */
    SHIPPING_FEE(FIXED_RATE.code + CodeAware.STEP),

    ;

    private final int code;

    public static DiscountPolicy from(int code) {
        for (DiscountPolicy policy : values()) {
            if (policy.getCode() == code) {
                return policy;
            }
        }

        throw new IllegalArgumentException("Invalid code: " + code);
    }

}
