package prototype.hexa.routertest.temp.service.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class QueryUserResult {
  private final Long id;
  private final String userName;
  private final String email;
}
