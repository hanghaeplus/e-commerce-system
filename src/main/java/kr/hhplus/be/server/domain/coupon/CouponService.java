package kr.hhplus.be.server.domain.coupon;

import kr.hhplus.be.server.common.exception.BusinessError;
import kr.hhplus.be.server.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    /**
     * 사용자의 사용 가능한 쿠폰 목록을 반환한다.
     */
    @Transactional(readOnly = true)
    public List<UserCoupon> getUsableUserCoupons(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BusinessException(BusinessError.COUPON_NOT_FOUND);
        }

        List<UserCoupon> userCoupons = couponRepository.findUsableUserCouponsByIds(ids);

        // 모든 쿠폰이 미사용인지 검사한다.
        userCoupons.stream()
                .filter(UserCoupon::isUsed)
                .findAny()
                .ifPresent(product -> {
                    throw new BusinessException(BusinessError.COUPON_ALREADY_USED);
                });

        // 모든 쿠폰이 사용 가능한지 검사한다.
        LocalDateTime now = LocalDateTime.now();
        userCoupons.stream()
                .map(UserCoupon::getCoupon)
                .filter(it -> !it.isUsable(now))
                .findAny()
                .ifPresent(product -> {
                    throw new BusinessException(BusinessError.COUPON_NOT_USABLE);
                });

        // 요청한 쿠폰을 모두 조회했는지 검사한다.
        if (userCoupons.size() == ids.size()) {
            throw new BusinessException(BusinessError.COUPON_NOT_FOUND);
        }

        return userCoupons;
    }

}
