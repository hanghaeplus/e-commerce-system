package kr.hhplus.be.server.domain.point;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.AuditableEntity;
import lombok.*;

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
