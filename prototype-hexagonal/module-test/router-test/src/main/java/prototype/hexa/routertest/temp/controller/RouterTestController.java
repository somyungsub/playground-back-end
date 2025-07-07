package prototype.hexa.routertest.temp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prototype.hexa.common.api.ApiResponse;
import prototype.hexa.config.router.property.RouterDatabaseConfigProperty;
import prototype.hexa.routertest.temp.service.RouterTestService;
import prototype.hexa.routertest.temp.service.query.QueryUserResult;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/router-test")
@RequiredArgsConstructor
public class RouterTestController {
//  private final RouterDatabaseConfigProperty routerDatabaseConfigProperty;
  private final RouterTestService routerTestService;

  @GetMapping
  ApiResponse<String> get() {
    return ApiResponse.ok("Test11");
  }

//  @GetMapping("/property")
//  ApiResponse<RouterDatabaseConfigProperty> getProperty() {
//    return ApiResponse.ok(routerDatabaseConfigProperty);
//  }

  @GetMapping("/users")
  ApiResponse<List<QueryUserResult>> users() {
    return ApiResponse.ok(routerTestService.findAllTestUser());
  }

  @GetMapping("/users/jpa")
  ApiResponse<List<QueryUserResult>> usersJpa() {
    return ApiResponse.ok(routerTestService.findAllTestUserJpa());
  }
}
