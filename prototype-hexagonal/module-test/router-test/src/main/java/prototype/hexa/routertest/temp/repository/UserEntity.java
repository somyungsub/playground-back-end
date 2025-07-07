package prototype.hexa.routertest.temp.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@ToString
class UserEntity {
  Long id;
  String userName;
  String email;
}
