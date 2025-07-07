package prototype.hexa.sample.adapter.`in`.web

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.reactive.function.client.WebClient
import prototype.hexa.common.annotation.WebAdapter
import prototype.hexa.common.api.ApiResponse
import prototype.hexa.sample.application.port.`in`.sample.SampleR2dbcUseCase
import prototype.hexa.sample.application.port.`in`.sample.SampleUseCase
import prototype.hexa.sample.domain.sample.Sample
import prototype.hexa.sample.domain.spread.event.SpreadEvent

@WebAdapter
@RequestMapping("/v1/kotlin/samples")
@RequiredArgsConstructor
@Slf4j
class KotlinWebAdapter(
        private val objectMapper: ObjectMapper,
        private val webClient: WebClient,
        private val sampleUseCase: SampleUseCase,
        private val sampleR2dbcUseCase: SampleR2dbcUseCase,
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(KotlinWebAdapter::class.java)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ApiResponse<Sample> {
        log.info("Kotlin Test {} ", id);
        val sample = sampleUseCase.findById(id)
        return ApiResponse.ok(sample)
    }

    @KafkaListener(topics = ["\${prototype.hexa.sample.event.topic-name}"], groupId = "\${prototype.hexa.sample.event.kotlin-group-id}")
    fun consumeEvent(data: ByteArray) {
        log.info("Kotlin -> event consume currentThread name {}", Thread.currentThread().name)
        val spreadEvent = objectMapper.readValue(data, SpreadEvent::class.java)
        log.info("Kotlin -> event consume : {}", spreadEvent)
    }

}

