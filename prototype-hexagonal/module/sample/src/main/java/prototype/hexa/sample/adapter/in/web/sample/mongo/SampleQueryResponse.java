package prototype.hexa.sample.adapter.in.web.sample.mongo;

import lombok.Builder;

@Builder
record SampleQueryResponse(String name, Long id) {
}
