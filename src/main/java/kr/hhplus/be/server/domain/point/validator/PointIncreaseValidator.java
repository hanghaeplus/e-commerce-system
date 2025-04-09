package kr.hhplus.be.server.domain.point.validator;

import kr.hhplus.be.server.common.exception.BusinessError;
import kr.hhplus.be.server.common.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
class PointIncreaseValidator implements PointValidator {

    @Override
    public boolean supports(PointValidationContext context) {
        return context.getActionType() == PointValidationContext.ActionType.INCREASE;
    }

    @Override
    public void validate(PointValidationContext context) {
        Long amount = context.getAmount();
        if (amount <= 0) {
            throw new BusinessException(BusinessError.POINT_INCREASE_INVALID_AMOUNT);
        }
    }

}
