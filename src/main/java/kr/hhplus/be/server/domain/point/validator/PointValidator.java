package kr.hhplus.be.server.domain.point.validator;

public interface PointValidator {

    boolean supports(PointValidationContext context);

    void validate(PointValidationContext context);

}
