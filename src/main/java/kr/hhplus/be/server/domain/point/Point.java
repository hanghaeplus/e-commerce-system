package kr.hhplus.be.server.domain.point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "id", nullable = false, updatable = false)
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
        this.balance += amount;
    }

    public void decrease(long amount) {
        this.balance -= amount;
    }

}
