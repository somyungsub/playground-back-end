package prototype.hexa.sample.adapter.out.mongo.spread;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "typeof_spreads")
@AllArgsConstructor
@Getter
@Builder
class SpreadTypeOfDocument {
  @Id
  private final String id;
  @Indexed(unique = true)
  private final String name;
  @DBRef
  private final List<RowTypeOfDocument> rows;
}

@AllArgsConstructor
@Getter
@Builder
@Document(collection = "typeof_rows")
class RowTypeOfDocument {
  @Id
  private final String id;
  private final int index;
  @DBRef
  private final List<ColumnTypeOfDocument> columns;
}

@AllArgsConstructor
@Getter
@Builder
@Document(collection = "typeof_columns")
class ColumnTypeOfDocument {
  @Id
  private final String id;
  private final String name;
  private final String value;
}