package prototype.hexa.common.validation;

import org.apache.commons.collections4.CollectionUtils;
import prototype.hexa.common.exception.GlobalException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


/**
 *  유효성 검증
 */
public abstract class SelfValidating<T extends SelfValidating<T>> {
  private static final Validator validator;

  static {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  /**
   *  유효성 검증
   */
  protected void validateSelf() {
    Set<ConstraintViolation<T>> violations = validator.validate((T) this);
    if (CollectionUtils.isNotEmpty(violations)) {
      throw new GlobalException("WNE-HRS-COMMON-0001", violations.stream().toList().get(0).getMessage());
    }
  }
}
