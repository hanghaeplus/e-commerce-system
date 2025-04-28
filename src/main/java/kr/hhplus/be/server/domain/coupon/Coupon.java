package kr.hhplus.be.server.domain.coupon;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.exception.BusinessError;
import kr.hhplus.be.server.common.exception.BusinessException;
import kr.hhplus.be.server.domain.common.AuditableEntity;
import lombok.*;
import org.hibernate.type.YesNoConverter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends AuditableEntity {

    /**
     * 아이디
     */
    @Id
    @Column(name = "coupon_id", nullable = false, updatable = false)
    private Long id;

    /**
     * 발급 시작일시
     */
    @Column(name = "issued_from", nullable = false)
    private LocalDateTime issuedFrom;

    /**
     * 발급 종료일시
     */
    @Column(name = "issued_to", nullable = false)
    private LocalDateTime issuedTo;

    /**
     * 사용 가능 시작일시
     */
    @Column(name = "usable_from", nullable = false)
    private LocalDateTime usableFrom;

    /**
     * 사용 가능 종료일시
     */
    @Column(name = "usable_to", nullable = false)
    private LocalDateTime usableTo;

    /**
     * 페기 여부
     */
    @Getter(AccessLevel.NONE)
    @Convert(converter = YesNoConverter.class)
    @Column(name = "revoked", nullable = false)
    private Boolean revoked;

    /**
     * 폐기일시
     */
    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;

    /**
     * 할인 정책
     */
    @Embedded
    private DiscountPolicy discountPolicy;

    // -------------------------------------------------------------------------------------------------


    // -------------------------------------------------------------------------------------------------

    /**
     * 발급할 수 있는지를 반환한다.
     */
    public boolean isIssuable(LocalDateTime dateTime) {
        if (isRevoked()) {
            return false;
        }

        if (this.issuedFrom == null || this.issuedTo == null) {
            throw new BusinessException(BusinessError.COMMON_NO_INITIALIZED_ENTITY);
        }

        return !dateTime.isBefore(this.issuedFrom) && !dateTime.isAfter(this.issuedTo);
    }

    /**
     * 사용할 수 있는지를 반환한다.
     */
    public boolean isUsable(LocalDateTime dateTime) {
        return !isBeforeActivation(dateTime) && !isExpired(dateTime);
    }

    /**
     * 아직 사용기간이 도래되지 않았는지를 반환한다.
     */
    public boolean isBeforeActivation(LocalDateTime dateTime) {
        if (this.usableFrom == null) {
            throw new BusinessException(BusinessError.COMMON_NO_INITIALIZED_ENTITY);
        }

        return dateTime.isBefore(this.usableFrom);
    }

    /**
     * 사용기간이 만료되었는지를 반환한다.
     */
    public boolean isExpired(LocalDateTime dateTime) {
        if (this.usableTo == null) {
            throw new BusinessException(BusinessError.COMMON_NO_INITIALIZED_ENTITY);
        }

        return dateTime.isAfter(this.usableTo);
    }

    public void revoke() {
        this.revoked = true;
        this.revokedAt = LocalDateTime.now();
    }

    public boolean isRevoked() {
        if (this.revoked == null || this.revokedAt == null) {
            throw new BusinessException(BusinessError.COMMON_NO_INITIALIZED_ENTITY);
        }

        return this.revoked;
    }

    public UserCoupon issueToUser(Long userId) {
        return UserCoupon.builder()
                .couponId(getId())
                .userId(userId)
                .issuedAt(LocalDateTime.now())
                .used(false)
                .coupon(this)
                .build();
    }

}
