import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class HisTest {
  @Test
  void test() {
    List<String> list = new ArrayList<>();
    List<String> list1 = list.stream().map(s -> s + "~test").toList();
    System.out.println(list1);
  }
}
