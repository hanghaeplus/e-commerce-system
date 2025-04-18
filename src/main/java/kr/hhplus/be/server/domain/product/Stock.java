package kr.hhplus.be.server.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.hhplus.be.server.common.exception.BusinessError;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.common.AuditableEntity;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "product_option_stock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends AuditableEntity {

    /**
     * 아이디
     */
    @Id
    @Column(name = "product_option_stock_id", nullable = false, updatable = false)
    private Long id;

    /**
     * 상품 옵션 아이디
     */
    @Column(name = "product_option_id", nullable = false, updatable = false, unique = true)
    private Long productOptionId;

    /**
     * 재고 수량
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public void increase(int amount) {
        if (amount <= 0) {
            throw new BusinessException(BusinessError.STOCK_INCREASE_INVALID_AMOUNT);
        }

        this.quantity += amount;
    }

    public void decrease(int amount) {
        if (amount <= 0) {
            throw new BusinessException(BusinessError.STOCK_DECREASE_INVALID_AMOUNT);
        }

        int decreasedQuantity = this.quantity - amount;
        if (decreasedQuantity < 0) {
            throw new BusinessException(BusinessError.STOCK_NOT_ENOUGH_QUANTITY);
        }

        this.quantity = decreasedQuantity;
    }

}
