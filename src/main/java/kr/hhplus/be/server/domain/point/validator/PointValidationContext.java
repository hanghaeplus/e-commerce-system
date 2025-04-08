package kr.hhplus.be.server.domain.point.validator;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PointValidationContext {

    private final Long balance;
    private final Long amount;
    private final ActionType actionType;

    public enum ActionType {
        INCREASE, DECREASE
    }

}
