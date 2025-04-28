package kr.hhplus.be.server.configuration.jpa.converter;

import jakarta.persistence.Converter;
import kr.hhplus.be.server.domain.coupon.DiscountRule;

@Converter
public class DiscountRuleConverter implements CodeAwareConverter<DiscountRule> {

    @Override
    public DiscountRule convertToEntityAttribute(Integer dbData) {
        return DiscountRule.from(dbData);
    }

}
