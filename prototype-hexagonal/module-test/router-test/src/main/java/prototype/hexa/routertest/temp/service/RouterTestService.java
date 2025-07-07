package prototype.hexa.routertest.temp.service;

import prototype.hexa.routertest.temp.service.query.QueryUserResult;

import java.util.List;

public interface RouterTestService {
  List<QueryUserResult> findAllTestUser();
  List<QueryUserResult> findAllTestUserJpa();
}
