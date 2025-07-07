package prototype.hexa.common.validation;

import prototype.hexa.common.exception.GlobalException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


/**
 *  유효성 검증
 */
public abstract class SelfBuilderValidating<T extends SelfBuilderValidating<T>> {
  private static final Validator validator;

  static {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  // 실제 객체 생성 메서드 - 서브클래스에서 구현
  protected abstract T buildInternal();

  // 공통 유효성 검사 포함 빌드 메서드
  public T build() {
    T instance = buildInternal();
    validate(instance);
    return instance;
  }

  protected void validate(T object) {
    Set<ConstraintViolation<T>> violations = validator.validate(object);
    if (!violations.isEmpty()) {
      throw new GlobalException("WNE-HRS-COMMON-0001", violations);
    }
  }
}
