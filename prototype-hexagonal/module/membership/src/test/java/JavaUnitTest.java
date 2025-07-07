import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class JavaUnitTest {
  @Test
  void test1() {
    String address = "a-b-c-d";
    List<String> list = Arrays.stream(address.split("-")).toList();
    System.out.println(list);
  }
  @Test
  void test2() {
    String address = "";
    List<String> list = Arrays.stream(address.split("-")).toList();
    System.out.println(list);
  }
  @Test
  void test3() {
    String address = null;
    List<String> list = Arrays.stream(StringUtils.defaultString(address).split("-")).toList();
    System.out.println(list);
  }
}
