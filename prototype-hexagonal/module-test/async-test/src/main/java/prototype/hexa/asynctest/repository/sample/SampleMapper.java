package prototype.hexa.asynctest.repository.sample;

import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Component;
import prototype.hexa.asynctest.domain.Sample;
import prototype.hexa.asynctest.domain.SampleInput;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static prototype.hexa.asynctest.Tables.SAMPLE_;
import static prototype.hexa.asynctest.Tables.SAMPLE_INPUT;


@Component
class SampleMapper {
  Sample toDomain(SampleJooq sampleJooq) {
    return Sample.builder()
      .id(sampleJooq.getId())
      .name(sampleJooq.getName())
      .code(sampleJooq.getCode())
      .build();
  }

  public Sample toDomain(Result<Record> fetch) {
//    SampleInput.builder()
//      .name(record.get(SAMPLE_INPUT.INPUT_NAME));
//    Stream<Sample> sampleStream = fetch.stream()
//      .map(record -> Sample.builder()
//        .id(record.get(SAMPLE_.ID))
//        .name(record.get(SAMPLE_.NAME))
//        .build()
//      );
//    for (Record record : fetch) {
//      Long sampleId = record.get(SAMPLE_.ID);
//      String sampleName = record.get(SAMPLE_.NAME);
//      String sampleCode = record.get(SAMPLE_.CODE);
//      String inputValue = record.get(SAMPLE_INPUT.INPUT_VALUE);
//
//      System.out.println("Sample ID: " + sampleId + ", Name: " + sampleName +
//        ", Code: " + sampleCode + ", Input Value: " + inputValue);
//    }
//
//    List<SampleInput> list = fetch.stream()
//      .map(record -> SampleInput.builder()
//        .id(record.get(SAMPLE_INPUT.ID))
//        .name(record.get(SAMPLE_INPUT.INPUT_NAME))
//        .value(record.get(SAMPLE_INPUT.INPUT_VALUE))
//        .build()
//      ).toList();

    // 부모 ID를 기준으로 그룹화하여 각 부모에 자식 리스트를 포함시킴


    Map<Long, Sample> collect = fetch.stream()
      .collect(groupingBy(
        record -> record.get(SAMPLE_.ID),
        collectingAndThen(
          toList(),
          records -> {
            Record record = records.get(0);

            List<SampleInput> inputs = records.stream()
              .map(r -> SampleInput.builder()
                .id(r.get(SAMPLE_INPUT.ID))
                .name(r.get(SAMPLE_INPUT.INPUT_NAME))
                .value(r.get(SAMPLE_INPUT.INPUT_VALUE))
                .build()
              ).toList();

            return Sample.builder()
              .id(record.get(SAMPLE_.ID))
              .name(record.get(SAMPLE_.NAME))
              .code(record.get(SAMPLE_.CODE))
              .inputs(inputs)
              .build();
          }
        )
      ));

    return collect.values().stream().findFirst().get();
  }
}
