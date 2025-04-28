package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.BusinessError;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.configuration.jpa.converter.OrderStatusConverter;
import kr.hhplus.be.server.domain.common.AuditableEntity;
import kr.hhplus.be.server.domain.coupon.UserCoupon;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends AuditableEntity {

    /**
     * 아이디
     */
    @Id
    @Column(name = "order_id", nullable = false, updatable = false)
    private Long id;

    /**
     * 사용자 아이디
     */
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    /**
     * 주문일시
     */
    @Column(name = "ordered_at", nullable = false, updatable = false)
    private LocalDateTime orderedAt;

    /**
     * 주문상태
     */
    @Convert(converter = OrderStatusConverter.class)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    // -------------------------------------------------------------------------------------------------

    /**
     * 주문에 사용한 쿠폰
     */
    @Transient
    private List<OrderCoupon> coupons = Collections.emptyList();

    // -------------------------------------------------------------------------------------------------

    public void addCoupon(UserCoupon userCoupon) {
        addCoupons(List.of(userCoupon));
    }

    public void addCoupons(List<UserCoupon> userCoupons) {
        List<OrderCoupon> those = new ArrayList<>(this.coupons);
        List<OrderCoupon> coupons = userCoupons.stream()
                .map(coupon -> OrderCoupon.builder()
                        .orderId(this.id)
                        .userCouponId(coupon.getId())
                        .build()
                )
                .toList();

        those.addAll(coupons);

        Set<Long> ids = new HashSet<>();
        Set<Long> userCouponIds = new HashSet<>();

        for (OrderCoupon that : those) {
            Long id = that.getId();
            if (id != null && !ids.add(id)) {
                throw new BusinessException(BusinessError.ORDER_DUPLICATED_COUPON);
            }

            Long userCouponId = that.getUserCouponId();
            if (!userCouponIds.add(userCouponId)) {
                throw new BusinessException(BusinessError.ORDER_DUPLICATED_COUPON);
            }
        }

        this.coupons = List.copyOf(those);
    }

}
