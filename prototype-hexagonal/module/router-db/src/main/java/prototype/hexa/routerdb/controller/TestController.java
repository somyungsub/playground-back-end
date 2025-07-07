package prototype.hexa.routerdb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prototype.hexa.common.api.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.router-db/")
public class TestController {
  @GetMapping("/test")
  public ApiResponse<String> test() {
    return ApiResponse.ok("test!!!");
  }
}
