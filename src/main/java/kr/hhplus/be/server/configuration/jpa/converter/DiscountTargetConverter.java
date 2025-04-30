package kr.hhplus.be.server.configuration.jpa.converter;

import jakarta.persistence.Converter;
import kr.hhplus.be.server.domain.coupon.DiscountTarget;

@Converter
public class DiscountTargetConverter implements CodeAwareConverter<DiscountTarget> {

    @Override
    public DiscountTarget convertToEntityAttribute(Integer dbData) {
        return DiscountTarget.from(dbData);
    }

}
