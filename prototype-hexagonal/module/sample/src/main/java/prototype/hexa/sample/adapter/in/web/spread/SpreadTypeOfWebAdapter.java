package prototype.hexa.sample.adapter.in.web.spread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import prototype.hexa.common.annotation.WebAdapter;
import prototype.hexa.common.api.ApiResponse;
import prototype.hexa.sample.application.port.in.spread.SpreadTypeOfUseCase;
import prototype.hexa.sample.application.port.in.spread.command.SpreadSaveCommand;
import prototype.hexa.sample.domain.spread.Spread;
import reactor.core.publisher.Mono;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/v1/typeof-spreads")
@Slf4j
class SpreadTypeOfWebAdapter {
  private final SpreadTypeOfUseCase spreadUseCase;
  private final SpreadWebMapper spreadWebMapper;

  @GetMapping("/{name}")
  public ApiResponse<SpreadQueryResponse> fetchByName(@PathVariable String name) {
    Spread spread = spreadUseCase.findByName2(name);
    SpreadQueryResponse response = spreadWebMapper.toQueryResponse(spread);
    return ApiResponse.ok(response);
  }

  @GetMapping("/async/{name}")
  public Mono<ApiResponse<SpreadQueryResponse>> asyncFetchByName(@PathVariable String name) {
    Mono<Spread> spreadMono = spreadUseCase.asyncFindByName2(name);
    return spreadMono.map(spread -> ApiResponse.ok(spreadWebMapper.toQueryResponse(spread)));
  }

  @PostMapping
  public ApiResponse<String> save(@RequestBody SpreadSaveRequest request) {
    SpreadSaveCommand saveCommand = spreadWebMapper.toSaveCommand(request);
    Spread spread = spreadUseCase.saveSpread2(saveCommand);
    return ApiResponse.ok(spread.getId());
  }
}
