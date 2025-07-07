package prototype.hexa.membership.domain.vo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

public record Address(
        String zipCode,
        String country,
        String city,
        String detailAddress
) {
  // TODO
  public static Address of(String address) {
    String addressStr = StringUtils.defaultString(address);
    List<String> list = Arrays.stream(addressStr.split("-")).toList();
    if (CollectionUtils.isEmpty(list)) {
      return new Address("", "", "", "");
    }

    return new Address(list.get(0), list.get(1), list.get(2), list.get(3));
  }

  public String getFullAddress() {
    return StringUtils.join(
            zipCode, "-",
            country, "-",
            city, "-",
            detailAddress
    );
  }
}
