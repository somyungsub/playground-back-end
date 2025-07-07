package prototype.hexa.sample.adapter.in.web.sample.rdb;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prototype.hexa.common.annotation.WebAdapter;
import prototype.hexa.common.api.ApiResponse;
import prototype.hexa.sample.application.port.in.sample.SampleUseCase;
import prototype.hexa.sample.application.port.in.sample.command.SampleSaveCommand;
import prototype.hexa.sample.application.port.in.sample.command.SampleUpdateCommand;
import prototype.hexa.sample.domain.sample.Sample;

@WebAdapter
@RequestMapping("/v1/samples")
@RequiredArgsConstructor
class SampleWebAdapter {
    private final SampleUseCase sampleUseCase;
    private final SampleWebMapper sampleWebMapper;

    @GetMapping("/{id}")
    ApiResponse<SampleResponse> findSample(@PathVariable Long id) {
        Sample result = sampleUseCase.findById(id);
        return ApiResponse.ok(sampleWebMapper.toResponse(result));
    }

    @GetMapping("/dsl/{id}")
    ApiResponse<SampleResponse> findSampleDsl(@PathVariable Long id) {
        Sample result = sampleUseCase.findByIdDsl(id);
        return ApiResponse.ok(sampleWebMapper.toResponse(result));
    }

    @PostMapping
    ApiResponse<SampleResponse> saveSample(@RequestBody SampleSaveRequest sampleSaveRequest) {
        SampleSaveCommand sampleSaveCommand = sampleWebMapper.toCommand(sampleSaveRequest);
        Sample result = sampleUseCase.saveSample(sampleSaveCommand);
        return ApiResponse.ok(sampleWebMapper.toResponse(result));
    }

    @PutMapping("/{id}")
    ApiResponse<SampleResponse> updateSample(@PathVariable Long id, @RequestBody SampleUpdateRequest sampleSaveRequest) {
        SampleUpdateCommand updateCommand = SampleUpdateCommand.builder()
          .name(sampleSaveRequest.name())
          .code(sampleSaveRequest.code())
          .inputs(sampleSaveRequest.inputs())
          .build();
        Sample result = sampleUseCase.updateSample(id, updateCommand);
        return ApiResponse.ok(sampleWebMapper.toResponse(result));
    }

    @DeleteMapping("/{id}")
    ApiResponse<Object> deleteSample(@PathVariable Long id) {
        sampleUseCase.deleteSample(id);
        return ApiResponse.ok(id);
    }
}
