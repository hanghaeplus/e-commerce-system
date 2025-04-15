package kr.hhplus.be.server.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum BusinessError {

    POINT_INCREASE_INVALID_AMOUNT(HttpStatus.BAD_REQUEST,
            "올바르지 않은 값으로 포인트를 증가할 수 없습니다."),
    POINT_DECREASE_INVALID_AMOUNT(HttpStatus.BAD_REQUEST,
            "올바르지 않은 값으로 포인트를 감소할 수 없습니다."),
    POINT_NOT_ENOUGH_BALANCE(HttpStatus.BAD_REQUEST,
            "포인트가 충분하지 않습니다."),
    ;

    private final HttpStatusCode status;
    private final String message;

}
