package prototype.hexa.sample.application.port.out.sample;


import prototype.hexa.common.port.out.CommandOutPort;
import prototype.hexa.common.port.out.QueryOutPort;
import prototype.hexa.sample.domain.sample.Sample;

public interface SampleMongoOutPort extends CommandOutPort<Sample, Long>, QueryOutPort<Sample, Long> {
}

