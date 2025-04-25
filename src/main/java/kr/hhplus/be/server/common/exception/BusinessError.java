package kr.hhplus.be.server.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum BusinessError {

    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST,
            "존재하지 않는 상품입니다."),
    PRODUCT_DISABLE(HttpStatus.BAD_REQUEST,
            "비활성화된 상품입니다."),
    PRODUCT_DUPLICATED_OPTION(HttpStatus.BAD_REQUEST,
            "하나의 상품에 동일한 옵션을 둘 이상 등록할 수 없습니다."),
    OPTION_DUPLICATED_TAG(HttpStatus.BAD_REQUEST,
            "하나의 상품 옵션에 동일한 태그를 둘 이상 등록할 수 없습니다."),

    POINT_INCREASE_INVALID_AMOUNT(HttpStatus.BAD_REQUEST,
            "올바르지 않은 값으로 포인트를 증가할 수 없습니다."),
    POINT_DECREASE_INVALID_AMOUNT(HttpStatus.BAD_REQUEST,
            "올바르지 않은 값으로 포인트를 감소할 수 없습니다."),
    POINT_NOT_ENOUGH_BALANCE(HttpStatus.BAD_REQUEST,
            "포인트가 충분하지 않습니다."),

    STOCK_INCREASE_INVALID_AMOUNT(HttpStatus.BAD_REQUEST,
            "올바르지 않은 값으로 재고를 증가할 수 없습니다."),

    STOCK_NOT_FOUND(HttpStatus.BAD_REQUEST,
            "존재하지 않는 재고입니다."),
    STOCK_DECREASE_INVALID_AMOUNT(HttpStatus.BAD_REQUEST,
            "올바르지 않은 값으로 재고를 감소할 수 없습니다."),
    STOCK_NOT_ENOUGH_QUANTITY(HttpStatus.BAD_REQUEST,
            "재고가 충분하지 않습니다."),

    COUPON_NOT_FOUND(HttpStatus.BAD_REQUEST,
            "존재하지 않는 쿠폰입니다."),
    COUPON_REVOKED(HttpStatus.BAD_REQUEST,
            "폐기된 쿠폰입니다."),
    COUPON_NOT_USABLE(HttpStatus.BAD_REQUEST,
            "사용할 수 없는 쿠폰입니다."),
    COUPON_BEFORE_ACTIVATION(HttpStatus.BAD_REQUEST,
            "쿠폰 사용기간이 도래되지 않았습니다."),
    COUPON_EXPIRED(HttpStatus.BAD_REQUEST,
            "쿠폰 사용기간이 만료되었습니다."),
    COUPON_ALREADY_USED(HttpStatus.BAD_REQUEST,
            "이미 사용한 쿠폰입니다."),

    COMMON_NO_INITIALIZED_ENTITY(HttpStatus.INTERNAL_SERVER_ERROR,
            "엔터티가 초기화되지 않았습니다."),
    COMMON_NO_CONSISTENT_ENTITY(HttpStatus.INTERNAL_SERVER_ERROR,
            "일관된 엔터티가 아닙니다."),
    ;

    private final HttpStatusCode status;
    private final String message;

}
