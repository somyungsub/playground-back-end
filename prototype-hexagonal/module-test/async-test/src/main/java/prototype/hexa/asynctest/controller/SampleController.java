package prototype.hexa.asynctest.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import prototype.hexa.asynctest.domain.Sample;
import prototype.hexa.asynctest.service.jooq.SampleJooqService;
import prototype.hexa.common.annotation.WebAdapter;
import prototype.hexa.common.api.ApiResponse;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/v1/jooq")
class SampleController {
  private final SampleJooqService sampleJooqService;

  @GetMapping("/samples/{id}")
  public ApiResponse<Sample> fetchSample(@PathVariable long id) {
    return ApiResponse.ok(sampleJooqService.findById(id));
  }
  @GetMapping("/samples-join/{id}")
  public ApiResponse<Sample> fetchJoinSample(@PathVariable long id) {
    return ApiResponse.ok(sampleJooqService.joinSample(id));
  }

  @GetMapping("/ctas/samples-mview/{id}")
  public ApiResponse<Sample>  fetchCtas(@PathVariable long id) {
    return ApiResponse.ok(sampleJooqService.findCtas(id));
  }

  @GetMapping("/ctas/{id}")
  public ApiResponse<String>  createTable(@PathVariable long id) {
    sampleJooqService.createTable(id);
    return ApiResponse.ok("OK");
  }

}
