package prototype.hexa.routertest.temp.repository;

import prototype.hexa.routertest.temp.service.query.QueryUserResult;

import java.util.List;

public interface UserRepository {
  List<QueryUserResult> findAllUser();
  List<QueryUserResult> findAllUserJpa();
}
