package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.AuditableEntity;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "order_coupon",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"order_id", "user_coupon_id"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCoupon extends AuditableEntity {

    /**
     * 아이디
     */
    @Id
    @Column(name = "order_coupon_id", nullable = false, updatable = false)
    private Long id;

    /**
     * 주문 아이디
     */
    @Column(name = "order_id", nullable = false, updatable = false)
    private Long orderId;

    /**
     * 사용자 쿠폰 아이디
     */
    @Column(name = "user_coupon_id", nullable = false, updatable = false)
    private Long userCouponId;

}
