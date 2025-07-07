import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class MathTest {

  @Test
  public void test() {
    System.out.println(0.1);
    System.out.println(0.2);
    System.out.println(0.2+0.1);
    BigDecimal num1 = new BigDecimal("0.1");
    BigDecimal num2 = new BigDecimal("0.2");
    System.out.println(num1.add(num2));
    System.out.println(num1.add(num2).compareTo(new BigDecimal("0.3")));
  }
}
