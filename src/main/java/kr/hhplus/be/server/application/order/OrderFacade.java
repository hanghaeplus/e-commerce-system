package kr.hhplus.be.server.application.order;

import kr.hhplus.be.server.domain.coupon.CouponService;
import kr.hhplus.be.server.domain.coupon.UserCoupon;
import kr.hhplus.be.server.domain.order.OrderService;
import kr.hhplus.be.server.domain.point.Point;
import kr.hhplus.be.server.domain.point.PointService;
import kr.hhplus.be.server.domain.product.Product;
import kr.hhplus.be.server.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final ProductService productService;
    private final CouponService couponService;
    private final PointService pointService;
    private final OrderService orderService;

    public void placeOrder(OrderRequirement.Place requirement) {
        List<Product> products = productService.getEnabledProductsByOptionIds(requirement.toOptionIds());

        List<UserCoupon> userCoupons = Collections.emptyList();
        if (!CollectionUtils.isEmpty(requirement.getUserCouponIds())) {
            userCoupons = couponService.getUsableUserCoupons(requirement.getUserCouponIds());
        }

        // TODO: 쿠폰 적용된 가격 계산

        productService.decreaseStocks(requirement.toChangeStockCommands());

        List<OrderRequirement.Item> items = requirement.getItems();
        Map<Long, Integer> optionMap = items.stream()
                .collect(toMap(OrderRequirement.Item::getOptionId, OrderRequirement.Item::getQuantity));
        int totalPrice = products.stream()
                .flatMap(product -> product.getOptions().stream()
                        .map(option -> {
                            int quantity = optionMap.get(option.getId());
                            int actualPrice = product.getActualPrice(option.getId());
                            return actualPrice * quantity;
                        })
                )
                .mapToInt(Integer::intValue)
                .sum();

        // TODO: 쿠폰 할인된 최종 금액이랑 비교 + 0원일 때를 상정하여 분기.
        Point point = pointService.getPoint(requirement.getUserId());
    }

}
