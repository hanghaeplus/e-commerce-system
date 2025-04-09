package kr.hhplus.be.server.domain.point.validator;

import org.immutables.value.Value;

@Value.Immutable
public interface PointValidationContext {

    Long getBalance();

    Long getAmount();

    ActionType getActionType();

    enum ActionType {
        INCREASE, DECREASE
    }

}
