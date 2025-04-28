package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.domain.common.CodeAware;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiscountRule implements CodeAware {

    /**
     * 정액 할인
     */
    FIXED_AMOUNT(CodeAware.STEP),

    /**
     * 정률 할인
     */
    FIXED_RATE(2 * CodeAware.STEP),

    ;

    private final int code;

    public static DiscountRule from(int code) {
        for (DiscountRule rule : values()) {
            if (rule.getCode() == code) {
                return rule;
            }
        }

        throw new IllegalArgumentException("Invalid code: " + code);
    }

}
