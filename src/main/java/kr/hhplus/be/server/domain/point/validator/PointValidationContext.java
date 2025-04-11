package kr.hhplus.be.server.domain.point.validator;

import org.immutables.value.Value;

@Value.Style(typeImmutable = "_Immutable*")
@Value.Immutable(copy = false)
public interface PointValidationContext {

    Long getBalance();

    Long getAmount();

    ActionType getActionType();

    enum ActionType {
        INCREASE, DECREASE
    }

}
