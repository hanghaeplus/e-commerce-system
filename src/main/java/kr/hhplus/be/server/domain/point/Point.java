package kr.hhplus.be.server.domain.point;

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
@Table(name = "point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends AuditableEntity {

    /**
     * 아이디
     */
    @Id
    @Column(name = "point_id", nullable = false, updatable = false)
    private Long id;

    /**
     * 사용자 아이디
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 잔액
     */
    @Column(name = "balance", nullable = false)
    private Long balance;

    public void increase(long amount) {
        if (amount <= 0) {
            throw new BusinessException(BusinessError.POINT_INCREASE_INVALID_AMOUNT);
        }

        this.balance += amount;
    }

    public void decrease(long amount) {
        if (amount <= 0) {
            throw new BusinessException(BusinessError.POINT_DECREASE_INVALID_AMOUNT);
        }

        long decreasedBalance = this.balance - amount;
        if (decreasedBalance < 0) {
            throw new BusinessException(BusinessError.POINT_NOT_ENOUGH_BALANCE);
        }

        this.balance = decreasedBalance;
    }

}
