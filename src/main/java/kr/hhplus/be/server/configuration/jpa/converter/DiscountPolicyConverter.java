package kr.hhplus.be.server.configuration.jpa.converter;

import jakarta.persistence.Converter;
import kr.hhplus.be.server.domain.coupon.DiscountPolicy;

@Converter
public class DiscountPolicyConverter implements CodeAwareConverter<DiscountPolicy> {

    @Override
    public DiscountPolicy convertToEntityAttribute(Integer dbData) {
        return DiscountPolicy.from(dbData);
    }

}
