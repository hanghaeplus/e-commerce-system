package kr.hhplus.be.server.domain.point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import kr.hhplus.be.server.domain.common.AuditableEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "point_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory extends AuditableEntity {

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
     * 사용 출처
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "origin_type", nullable = false)
    private OriginType originType;

    /**
     * 금액
     */
    @Column(name = "amount", nullable = false)
    private Long amount;

    // -------------------------------------------------------------------------------------------------

    public enum OriginType {
        /**
         * 포인트 충전
         */
        CHARGE,

        /**
         * 주문 결제
         */
        PAYMENT,
    }

}
