package prototype.hexa.sample.domain.sample;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SampleInput {
    Long id;
    String name;
    String value;

    public static SampleInput withId(Long id, String name, String value) {
        return builder()
                .id(id)
                .name(name)
                .value(value)
                .build();
    }
    public static SampleInput withoutId(String name, String value) {
        return withId(null, name, value);
    }
}
