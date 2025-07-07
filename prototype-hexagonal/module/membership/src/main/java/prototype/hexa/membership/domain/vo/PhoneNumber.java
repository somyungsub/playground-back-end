package prototype.hexa.membership.domain.vo;

import org.apache.commons.lang3.StringUtils;

public record PhoneNumber(
  String firstNumber,
  String secondNumber,
  String thirdNumber,
  PhoneNumberType phoneNumberType
) {
  public static PhoneNumber of(String phoneNumber) {
    String[] split = StringUtils.split(phoneNumber, "-");
    return new PhoneNumber(split[0], split[1], split[2], PhoneNumberType.valueOf(split[3]));
  }

  public static PhoneNumber empty() {
    return new PhoneNumber("", "", "", PhoneNumberType.UNKNOWN);
  }

  public String getFullPhoneNumber() {
    if (phoneNumberType == PhoneNumberType.UNKNOWN) {
      return "";
    }
    return StringUtils.join(firstNumber, "-", secondNumber, "-", thirdNumber, "-", phoneNumberType.name());
  }
  private enum PhoneNumberType {
    HOME("집"),
    MOBILE("휴대폰"),
    UNKNOWN("")
    ;
    private final String name;
    PhoneNumberType(String name) {
      this.name = name;
    }
  }
}
