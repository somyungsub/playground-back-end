package prototype.hexa.routertest.temp.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import prototype.hexa.routertest.temp.service.query.QueryUserResult;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
class UserRepositoryJdbc implements UserRepository {
  private final JdbcTemplate jdbcTemplate;
  private final UserJpaRepository userJpaRepository;

  @Override
  public List<QueryUserResult> findAllUser() {
//    List<UserEntity> query = jdbcTemplate.query("select * from his.tbl_user", (rs, rowNum) -> {
    List<UserEntity> query = jdbcTemplate.query("select * from tbl_user", (rs, rowNum) -> {
      UserEntity userEntity = UserEntity.builder()
              .id(rs.getLong("id"))
              .userName(rs.getString("username"))
              .email(rs.getString("email"))
              .build();
      log.info("UserEntity : {} ", userEntity.toString());
      return userEntity;
    });
    log.info("query : {} ", query);
    return toResult(query);
  }

  @Override
  public List<QueryUserResult> findAllUserJpa() {
    List<UserJpaEntity> all = userJpaRepository.findAll();
    return toResultJpa(all);
  }

  private List<QueryUserResult> toResult(List<UserEntity> entities) {
    return entities.stream().map(entity -> QueryUserResult.builder()
            .id(entity.getId())
            .userName(entity.getUserName())
            .email(entity.getEmail())
            .build()
    ).toList();
  }
  private List<QueryUserResult> toResultJpa(List<UserJpaEntity> entities) {
    return entities.stream().map(entity -> QueryUserResult.builder()
            .id(entity.getId())
            .userName(entity.getUserName())
            .email(entity.getEmail())
            .build()
    ).toList();
  }
}
