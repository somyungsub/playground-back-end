package prototype.hexa.sample.adapter.out.mongo.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "sample_input")
@Getter
@AllArgsConstructor
@NoArgsConstructor
class SampleInputDocument {

    @Id
    private String id;  // MongoDB는 주로 String 타입의 ObjectId를 사용합니다

    @Field(name = "input_id")
    private Long inputId;

    @Field(name = "input_name")
    private String name;

    @Field(name = "input_value")
    private String value;

    @DBRef  // MongoDB에서 참조 관계를 표현할 때 사용
    private SampleDocument sampleDocument;

//    static SampleInputEntity of(String id, String name, String value) {
//        return new SampleInputEntity(id, name, value, null);
//    }

//    void setSampleJpaEntity(SampleJpaEntity sampleJpaEntity) {
//        this.sampleJpaEntity = sampleJpaEntity;
//    }

}
