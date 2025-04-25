package kr.hhplus.be.server.domain.coupon;

import java.util.List;

public interface CouponRepository {

    List<UserCoupon> findUsableUserCouponsByIds(List<Long> ids);

}
