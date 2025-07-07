package prototype.hexa.sample.application.service.sample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import prototype.hexa.common.annotation.UseCase;
import prototype.hexa.common.exception.GlobalException;
import prototype.hexa.sample.application.port.in.sample.SampleNodeUseCase;
import prototype.hexa.sample.application.port.out.sample.SampleOutPort;
import prototype.hexa.sample.application.port.out.sample.SampleR2dbcOutPort;
import prototype.hexa.sample.config.properties.SampleProperties;
import prototype.hexa.sample.domain.sample.Sample;
import prototype.hexa.sample.domain.sample.SampleNode;
import prototype.hexa.sample.domain.sample.vo.NodeResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
@Slf4j
class SampleNodeUseCaseService implements SampleNodeUseCase {
  private final SampleR2dbcOutPort sampleR2dbcOutPort;
  private final SampleOutPort sampleOutPort;
  private final WebClient webClient;
  private final RestTemplate restTemplate;
  private final SampleProperties sampleProperties;

  @Override
  public Mono<Sample> findSample(Long id) {
    return sampleR2dbcOutPort.findById(id);
  }

  @Override
  public SampleNode syncFindSample(Long id) {
    NodeResult nodeResult = restTemplate.getForObject(createUri(id), NodeResult.class);
    Sample sample = Optional
            .ofNullable(sampleOutPort.findById(id))
            .orElseThrow(() -> new GlobalException("WNE-HRS-SAMPLE-0001", id));
    return SampleNode.of(sample, nodeResult);
  }

  @Override
  public Mono<SampleNode> asyncFindSample(Long id) {
    return webClient.get()
            .uri(createUri(id))
            .retrieve()
            .bodyToMono(NodeResult.class)
            .flatMap(response ->
                    sampleR2dbcOutPort.findById(id)
                            .map(sample -> SampleNode.of(sample, response))
                            .switchIfEmpty(Mono.error(new GlobalException("WNE-HRS-SAMPLE-0001", id)))
            )
            .doOnError(error -> {
              // 로그에 에러 출력
              System.err.println("Error occurred: " + error.getMessage());
            });
  }

  @Override
  public Flux<SampleNode> asyncFindSamples(Long id) {
    return Flux.range(1, 10) // 10번 요청
            .flatMap(i -> webClient.get()
                    .uri(createUri(id))
                    .retrieve()
                    .bodyToMono(NodeResult.class)
                    .flatMap(response ->
                            sampleR2dbcOutPort.findById(id)
                                    .map(sample -> SampleNode.of(i, sample, response))
                                    .switchIfEmpty(Mono.error(new GlobalException("WNE-HRS-SAMPLE-0001", id)))
                    )
                    .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()))
            );
  }


  private String createUri(Long id) {
    return UriComponentsBuilder.fromUriString(sampleProperties.getNodeUrlMongo())
            .pathSegment("{id}")
            .buildAndExpand(id)
            .toUriString();
  }


}
