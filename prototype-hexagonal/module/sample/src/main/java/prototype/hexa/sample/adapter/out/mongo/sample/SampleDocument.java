package prototype.hexa.sample.adapter.out.mongo.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Index;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "samples")
@Getter
@AllArgsConstructor
@NoArgsConstructor
class SampleDocument {

    @Id
    private String id;
    @Field(name = "sample_id")
    private Long sampleId;
    @Field(name = "sample_name")
    private String name;
    @Field(name = "sample_code")
    private String code;
    private List<SampleInputDocument> inputs;

    public static SampleDocument of(Long sampleId, String name, String code,List<SampleInputDocument> inputList) {
        return new SampleDocument(null, sampleId, name , code, inputList);
    }


//    public static SampleDocument of(String id, String name, String value, List<SampleInputEntity> inputs) {
//        return new SampleDocument(id, name, value, inputs);
//    }
//
//    public void addInput(SampleInputEntity input) {
//        this.inputs.add(input);
//    }

}
