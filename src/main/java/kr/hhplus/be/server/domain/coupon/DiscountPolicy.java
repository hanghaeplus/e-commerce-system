package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import kr.hhplus.be.server.configuration.jpa.converter.DiscountRuleConverter;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountPolicy {

    /**
     * 할인 방법
     */
    @Convert(converter = DiscountRuleConverter.class)
    @Column(name = "discount_rule", nullable = false)
    private DiscountRule discountRule;

    /**
     * 할인 금액
     */
    @Column(name = "discount_amount", nullable = false)
    private Integer discountAmount;

    /**
     * 최대 할인 가능 금액
     */
    @Column(name = "max_discount_amount")
    private Integer maxDiscountAmount;

    // -------------------------------------------------------------------------------------------------


    // -------------------------------------------------------------------------------------------------

}
