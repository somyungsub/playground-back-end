package prototype.hexa.membership.domain.vo;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Email(
  String name,
  String domain
) {
  public static Email of(String email) {
    Pattern pattern = Pattern.compile("(.+)@(.+)");
    Matcher matcher = pattern.matcher(email);

    if (matcher.matches()) {
      String name = matcher.group(1);
      String domain = matcher.group(2);
      return new Email(name,domain);
    }
    return new Email("","");
  }

  public String getFullEmailAddress() {
    return StringUtils.join(name, "@", domain);
  }
}
