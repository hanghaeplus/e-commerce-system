package kr.hhplus.be.server.configuration.jpa.converter;

import jakarta.persistence.Converter;
import kr.hhplus.be.server.domain.order.OrderStatus;

@Converter
public class OrderStatusConverter implements CodeAwareConverter<OrderStatus> {

    @Override
    public OrderStatus convertToEntityAttribute(Integer dbData) {
        return OrderStatus.from(dbData);
    }

}
