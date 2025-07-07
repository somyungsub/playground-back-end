package prototype.hexa.sample.adapter.out.mongo.spread;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import prototype.hexa.sample.domain.spread.Spread;
import prototype.hexa.sample.domain.spread.vo.Column;
import prototype.hexa.sample.domain.spread.vo.Row;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "spreads")
@AllArgsConstructor
@Getter
@Builder
public class SpreadDocument {
  @Id
  private final String id;
  @Indexed(unique = true)
  private final String name;
  private final List<RowDocument> rows;
}

@AllArgsConstructor
@Getter
@Builder
class RowDocument {
  private final int index;
  private final List<ColumnDocument> columns;
}

@AllArgsConstructor
@Getter
@Builder
class ColumnDocument {
  private final String name;
  private final String value;
}
