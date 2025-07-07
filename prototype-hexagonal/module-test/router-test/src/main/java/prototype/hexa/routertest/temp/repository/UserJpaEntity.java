package prototype.hexa.routertest.temp.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "TBL_USER_JPA")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "USER_NAME")
  private String userName;

  @Column(name = "EMAIL")
  private String email;
}
