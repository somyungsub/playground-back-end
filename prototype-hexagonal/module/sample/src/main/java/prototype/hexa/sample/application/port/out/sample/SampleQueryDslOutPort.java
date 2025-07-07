package prototype.hexa.sample.application.port.out.sample;


import prototype.hexa.common.port.in.QueryUseCase;
import prototype.hexa.sample.domain.sample.Sample;

import java.util.Optional;

public interface SampleQueryDslOutPort  {
    Sample findByIdDsl(Long sampleId);
}
