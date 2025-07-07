package prototype.hexa.sample.adapter.in.web.sample.r2dbc;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prototype.hexa.common.annotation.WebAdapter;
import prototype.hexa.common.api.ApiResponse;
import prototype.hexa.sample.application.port.in.sample.SampleR2dbcUseCase;
import prototype.hexa.sample.application.port.in.sample.command.SampleSaveCommand;
import prototype.hexa.sample.domain.sample.Sample;
import reactor.core.publisher.Mono;

@WebAdapter
@RequestMapping("/v1/r2dbc/samples")
@RequiredArgsConstructor
class SampleR2dbcWebAdapter {
    private final SampleR2dbcUseCase sampleUseCase;
    private final R2dbcWebMapper r2dbcWebMapper;

    @GetMapping("/{id}")
    Mono<ApiResponse<SampleQueryResponse>> findSampleById(@PathVariable Long id) {
        Mono<Sample> sampleMono = sampleUseCase.findById(id);
        return sampleMono.map(sample -> ApiResponse.ok(r2dbcWebMapper.toResponse(sample)));
    }
    @GetMapping
    Mono<ApiResponse<SampleQueryResponse>> fetchSampleByName(@RequestParam String name) {
        Mono<Sample> sampleMono = sampleUseCase.findByName(name);
        return sampleMono.map(sample -> ApiResponse.ok(r2dbcWebMapper.toResponse(sample)));
    }

    @PostMapping
    Mono<ApiResponse<SampleQueryResponse>> saveSample(@RequestBody SampleSaveRequest sampleSaveRequest) {
        SampleSaveCommand sampleSaveCommand = r2dbcWebMapper.toCommand(sampleSaveRequest);
        Mono<Sample> sampleMono = sampleUseCase.saveSample(sampleSaveCommand);
        return sampleMono.map(sample -> ApiResponse.ok(r2dbcWebMapper.toResponse(sample)));
    }
}
