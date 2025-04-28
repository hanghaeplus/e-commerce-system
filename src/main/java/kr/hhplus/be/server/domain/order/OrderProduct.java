package kr.hhplus.be.server.domain.order;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.AuditableEntity;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "order_product",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"order_id", "product_option_id"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends AuditableEntity {

    /**
     * 아이디
     */
    @Id
    @Column(name = "order_product_id", nullable = false, updatable = false)
    private Long id;

    /**
     * 주문 아이디
     */
    @Column(name = "order_id", nullable = false, updatable = false)
    private Long orderId;

    /**
     * 상품 옵션 아이디
     */
    @Column(name = "product_option_id", nullable = false, updatable = false)
    private Long optionId;

    /**
     * 주문 상품 수
     */
    @Column(name = "quantity", nullable = false, updatable = false)
    private Integer quantity;

    // -------------------------------------------------------------------------------------------------

    /**
     * 상품 이름 (스냅샷)
     */
    @Column(name = "product_name", nullable = false, updatable = false)
    private String productName;

    /**
     * 상품 옵션 이름 (스냅샷)
     */
    @Column(name = "product_option_name", nullable = false, updatable = false)
    private String optionName;

    /**
     * 할인 전 개당 가격
     */
    @Column(name = "unit_price_before_discount", nullable = false, updatable = false)
    private Integer unitPriceBeforeDiscount;

    /**
     * 할인 금액
     */
    @Column(name = "discount_amount", nullable = false, updatable = false)
    private Integer discountAmount;

    /**
     * 할인 후 개당 가격
     */
    @Column(name = "unit_price_after_discount", nullable = false, updatable = false)
    private Integer unitPriceAfterDiscount;

}
