package kr.hhplus.be.server.domain.point.validator;

import org.springframework.stereotype.Component;

import kr.hhplus.be.server.common.exception.BusinessError;
import kr.hhplus.be.server.common.exception.BusinessException;

@Component
class PointDecreaseValidator implements PointValidator {

    @Override
    public boolean supports(PointValidationContext context) {
        return context.getActionType() == PointValidationContext.ActionType.DECREASE;
    }

    @Override
    public void validate(PointValidationContext context) {
        Long amount = context.getAmount();
        if (amount <= 0) {
            throw new BusinessException(BusinessError.POINT_DECREASE_INVALID_AMOUNT);
        }

        long decreasedBalance = context.getBalance() - amount;
        if (decreasedBalance < 0) {
            throw new BusinessException(BusinessError.POINT_NOT_ENOUGH_BALANCE);
        }
    }

}
