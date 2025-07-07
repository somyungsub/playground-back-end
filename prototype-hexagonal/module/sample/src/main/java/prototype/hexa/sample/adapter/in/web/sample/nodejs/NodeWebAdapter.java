package prototype.hexa.sample.adapter.in.web.sample.nodejs;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import prototype.hexa.common.annotation.WebAdapter;
import prototype.hexa.common.api.ApiResponse;
import prototype.hexa.sample.application.port.in.sample.SampleNodeUseCase;
import prototype.hexa.sample.domain.sample.SampleNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@WebAdapter
@RequestMapping("/v1/node")
@RequiredArgsConstructor
class NodeWebAdapter {

    private final SampleNodeUseCase sampleNodeUseCase;
    private final NodeWebMapper nodeWebMapper;

    @GetMapping("/sync/{id}")
    public ApiResponse<NodeSampleRulesQueryResponse> sync(@PathVariable Long id) {
        SampleNode sampleNode = sampleNodeUseCase.syncFindSample(id);
        return ApiResponse.ok(nodeWebMapper.toResponse(sampleNode));

    }

    @GetMapping("/async/{id}")
    public Mono<ApiResponse<NodeSampleRulesQueryResponse>> async(@PathVariable Long id) {
        return sampleNodeUseCase
                .asyncFindSample(id)
                .map(sampleNode -> ApiResponse.ok(nodeWebMapper.toResponse(sampleNode)));
    }
    @GetMapping("/async/list/{id}")
    public Mono<ApiResponse<List<NodeSampleRulesQueryResponse>>> asyncList(@PathVariable Long id) {
        return sampleNodeUseCase
                .asyncFindSamples(id)
                .collectList()
                .map(samples -> ApiResponse.ok(nodeWebMapper.toResponse(samples)));
    }
    @GetMapping("/async/flux/{id}")
    public Flux<ApiResponse<NodeSampleRulesQueryResponse>> reactive4(@PathVariable Long id) {
        return sampleNodeUseCase
                .asyncFindSamples(id)
                .map(sampleNode -> ApiResponse.ok(nodeWebMapper.toResponse(sampleNode)));
    }

}
