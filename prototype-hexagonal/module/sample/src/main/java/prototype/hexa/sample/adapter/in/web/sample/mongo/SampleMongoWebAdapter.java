package prototype.hexa.sample.adapter.in.web.sample.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prototype.hexa.common.annotation.WebAdapter;
import prototype.hexa.common.api.ApiResponse;
import prototype.hexa.sample.application.port.in.sample.SampleMongoUseCase;
import prototype.hexa.sample.application.port.in.sample.command.SampleSaveCommand;
import prototype.hexa.sample.application.port.in.sample.command.SampleUpdateCommand;
import prototype.hexa.sample.domain.sample.Sample;

@WebAdapter
@RequestMapping("/v1/mongo/samples")
@RequiredArgsConstructor
class SampleMongoWebAdapter {
    private final SampleMongoUseCase sampleUseCase;
    private final MongoWebMapper mongoWebMapper;

    @GetMapping("/{id}")
    ApiResponse<SampleQueryResponse> findSample(@PathVariable Long id) {
        Sample result = sampleUseCase.findById(id);
        return ApiResponse.ok(mongoWebMapper.toResponse(result));
    }

    @PostMapping
    ApiResponse<SampleQueryResponse> saveSample(@RequestBody SampleSaveRequest sampleSaveRequest) {
        SampleSaveCommand sampleSaveCommand = mongoWebMapper.toCommand(sampleSaveRequest);
        Sample result = sampleUseCase.saveSample(sampleSaveCommand);
        return ApiResponse.ok(mongoWebMapper.toResponse(result));
    }

    @PutMapping("/{id}")
    ApiResponse<SampleQueryResponse> updateSample(@PathVariable Long id, @RequestBody SampleUpdateRequest sampleUpdateRequest) {
        SampleUpdateCommand updateCommand = SampleUpdateCommand.builder()
          .name(sampleUpdateRequest.name())
          .code(sampleUpdateRequest.code())
          .build();
        Sample result = sampleUseCase.updateSample(id, updateCommand);
        return ApiResponse.ok(mongoWebMapper.toResponse(result));
    }

    @DeleteMapping("/{id}")
    ApiResponse<Object> deleteSample(@PathVariable Long id) {
        sampleUseCase.deleteSample(id);
        return ApiResponse.ok(id);
    }
}
