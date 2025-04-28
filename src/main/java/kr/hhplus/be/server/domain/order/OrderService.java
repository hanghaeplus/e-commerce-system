package kr.hhplus.be.server.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order placeOrder(OrderCommand.Place command) {
        Order order = Order.builder()
                .userId(command.getUserId())
                .orderedAt(LocalDateTime.now())
                .status(OrderStatus.PAYMENT_WAITING)
                .build();

        List<Long> userCouponIds = command.getUserCouponIds();
        if (!CollectionUtils.isEmpty(userCouponIds)) {
            List<OrderCoupon> orderCoupons = userCouponIds.stream()
                    .map(userCouponId -> OrderCoupon.builder()
                            .userCouponId(userCouponId)
                            .build()
                    )
                    .toList();
            order.addCoupons(orderCoupons);
        }

        orderRepository.saveOrder(order);

        return order;
    }

}
