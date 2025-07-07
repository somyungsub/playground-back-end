package prototype.hexa.sample.config.properties;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Delegate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@AllArgsConstructor
@ConstructorBinding
@ToString
@ConfigurationProperties(prefix = "prototype.hexa.sample")
public class SampleProperties {
    @Getter
    private final String nodeUrlMongo;
    @Getter
    private final String nodeUrlRedis;
    @Delegate
    private final EventProperties event;

    private record EventProperties(String topicName, String groupId, String groupId2, String kotlinGroupId) { }

//    private final List<String> requestPaths;
//    public String getPath(int index) {
//        Preconditions.checkArgument(index < requestPaths.size(), "인덱스 값이 requestPaths 사이즈 보다 작아야합니다.", index);
//        Preconditions.checkArgument(index >= 0, "인덱스 값은 0 보다 크거나 같아야합니다.", index);
//        return requestPaths.get(index);

//    }
}
