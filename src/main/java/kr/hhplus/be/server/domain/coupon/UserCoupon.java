package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.BusinessError;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.common.AuditableEntity;
import lombok.*;
import org.hibernate.type.YesNoConverter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCoupon extends AuditableEntity {

    /**
     * 아이디
     */
    @Id
    @Column(name = "user_coupon_id", nullable = false, updatable = false)
    private Long id;

    /**
     * 아이디
     */
    @Column(name = "coupon_id", nullable = false, updatable = false)
    private Long couponId;

    /**
     * 사용자 아이디
     */
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    /**
     * 발급일시
     */
    @Column(name = "issued_at", nullable = false, updatable = false)
    private LocalDateTime issuedAt;

    /**
     * 사용 여부
     */
    @Getter(AccessLevel.NONE)
    @Convert(converter = YesNoConverter.class)
    @Column(name = "used", nullable = false)
    private Boolean used;

    // -------------------------------------------------------------------------------------------------

    /**
     * 쿠폰 정보
     */
    @Transient
    private Coupon coupon;

    // -------------------------------------------------------------------------------------------------

    public void use() {
        if (isUsed()) {
            throw new BusinessException(BusinessError.COUPON_ALREADY_USED);
        }

        if (this.coupon == null) {
            throw new BusinessException(BusinessError.COMMON_NO_INITIALIZED_ENTITY);
        }

        if (this.coupon.isRevoked()) {
            throw new BusinessException(BusinessError.COUPON_REVOKED);
        }

        LocalDateTime now = LocalDateTime.now();
        if (this.coupon.isBeforeActivation(now)) {
            throw new BusinessException(BusinessError.COUPON_BEFORE_ACTIVATION);
        }

        if (this.coupon.isExpired(now)) {
            throw new BusinessException(BusinessError.COUPON_EXPIRED);
        }

        this.used = true;
    }

    public boolean isUsed() {
        if (this.used == null) {
            throw new BusinessException(BusinessError.COMMON_NO_INITIALIZED_ENTITY);
        }

        return this.used;
    }

    public void setCoupon(Coupon coupon) {
        if (!Objects.equals(this.couponId, coupon.getId())) {
            throw new BusinessException(BusinessError.COMMON_NO_CONSISTENT_ENTITY);
        }

        this.coupon = coupon;
    }

}
