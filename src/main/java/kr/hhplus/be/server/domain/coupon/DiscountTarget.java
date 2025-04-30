package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.domain.common.CodeAware;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiscountTarget implements CodeAware {

    /**
     * 장바구니
     */
    CART(CodeAware.STEP),

    /**
     * 분류
     */
    CATEGORY(2 * CodeAware.STEP),

    /**
     * 브랜드
     */
    BRAND(3 * CodeAware.STEP),

    /**
     * 특정 상품
     */
    PRODUCT(4 * CodeAware.STEP),

    /**
     * 배송비
     */
    SHIPPING_FEE(5 * CodeAware.STEP),

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
