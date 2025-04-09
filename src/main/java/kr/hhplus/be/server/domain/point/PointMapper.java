package kr.hhplus.be.server.domain.point;

import kr.hhplus.be.server.domain.point.validator.ImmutablePointValidationContext;
import kr.hhplus.be.server.domain.point.validator.PointValidationContext;

public class PointMapper {

    public static PointValidationContext toIncreaseContext(PointCommand.Increase command, long balance) {
        return ImmutablePointValidationContext.builder()
                .balance(balance)
                .amount(command.getAmount())
                .actionType(PointValidationContext.ActionType.INCREASE)
                .build();
    }

    public static PointValidationContext toDecreaseContext(PointCommand.Decrease command, long balance) {
        return ImmutablePointValidationContext.builder()
                .balance(balance)
                .amount(command.getAmount())
                .actionType(PointValidationContext.ActionType.DECREASE)
                .build();
    }

}
