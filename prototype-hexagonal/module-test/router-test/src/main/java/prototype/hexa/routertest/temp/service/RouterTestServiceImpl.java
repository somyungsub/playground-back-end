package prototype.hexa.routertest.temp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prototype.hexa.routertest.temp.repository.UserRepository;
import prototype.hexa.routertest.temp.service.query.QueryUserResult;

import java.util.List;

@Service
@RequiredArgsConstructor
class RouterTestServiceImpl implements RouterTestService {
  private final UserRepository userRepository;

  @Override
  public List<QueryUserResult> findAllTestUser() {
    return userRepository.findAllUser();
  }

  @Override
  public List<QueryUserResult> findAllTestUserJpa() {
    return userRepository.findAllUserJpa();
  }
}
