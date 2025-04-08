package kr.hhplus.be.server.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessError {

    POINT_INCREASE_INVALID_AMOUNT("invalid_increase_point_amount", HttpStatus.BAD_REQUEST),
    POINT_DECREASE_INVALID_AMOUNT("invalid_decrease_point_amount", HttpStatus.BAD_REQUEST),
    POINT_NOT_ENOUGH_BALANCE("not_enough_balance", HttpStatus.BAD_REQUEST),

    ;

    private final String message;
    private final HttpStatusCode status;

}
