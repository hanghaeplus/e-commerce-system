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
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    /**
     * 출처 종류
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "origin_type", nullable = false, updatable = false)
    private OriginType originType;

    /**
     * 금액
     */
    @Column(name = "amount", nullable = false, updatable = false)
    private Long amount;

}
