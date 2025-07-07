package prototype.hexa.sample.adapter.out.mongo.spread;

import org.springframework.stereotype.Component;
import prototype.hexa.sample.domain.spread.Spread;
import prototype.hexa.sample.domain.spread.vo.Column;
import prototype.hexa.sample.domain.spread.vo.Row;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
class SpreadOutPortMapper {
  SpreadDocument toDocument(Spread spread) {
    List<RowDocument> rows = spread.getRows().stream()
            .map(this::toRowDocument)
            .collect(toList());

    return SpreadDocument.builder()
            .id(spread.getId())
            .name(spread.getName())
            .rows(rows)
            .build();
  }

  private RowDocument toRowDocument(Row row) {
    List<ColumnDocument> columns = row.getColumns().stream().map(this::toColumnDocument).collect(toList());
    return RowDocument.builder()
            .index(row.getIndex())
            .columns(columns)
            .build();
  }

  private ColumnDocument toColumnDocument(Column column) {
    return ColumnDocument.builder()
            .value(column.getValue())
            .name(column.getName())
            .build();
  }

  Spread toDomain(SpreadDocument document) {
    List<Row> rows = document.getRows().stream()
            .map(this::toRow)
            .collect(toList());
    return Spread.builder()
            .id(document.getId())
            .name(document.getName())
            .rows(rows)
            .build();
  }

  private Row toRow(RowDocument rowDocument) {
    List<Column> columns = rowDocument.getColumns().stream().map(this::toColumn).collect(toList());
    return Row.builder()
            .index(rowDocument.getIndex())
            .columns(columns)
            .build();
  }
  private Row toRowTypeOf(RowTypeOfDocument rowDocument) {
    List<Column> columns = rowDocument.getColumns().stream().map(this::toColumnTypeOf).collect(toList());
    return Row.builder()
            .index(rowDocument.getIndex())
            .columns(columns)
            .build();
  }

  private Column toColumn(ColumnDocument columnDocument) {
    return Column.builder()
            .name(columnDocument.getName())
            .value(columnDocument.getValue())
            .build();
  }
  private Column toColumnTypeOf(ColumnTypeOfDocument columnDocument) {
    return Column.builder()
            .name(columnDocument.getName())
            .value(columnDocument.getValue())
            .build();
  }


  public SpreadTypeOfDocument toDocument2(Spread spread) {
    List<RowTypeOfDocument> rows = spread.getRows().stream()
            .map(this::toRowDocument2)
            .collect(toList());

    return SpreadTypeOfDocument.builder()
            .id(spread.getId())
            .name(spread.getName())
            .rows(rows)
            .build();
  }

  RowTypeOfDocument toRowDocument2(Row row) {
    List<ColumnTypeOfDocument> columns = row.getColumns().stream().map(this::toColumnDocumentTypeOf).collect(toList());
    return RowTypeOfDocument.builder()
            .index(row.getIndex())
            .columns(columns)
            .build();
  }

  ColumnTypeOfDocument toColumnDocumentTypeOf(Column column) {
    return ColumnTypeOfDocument.builder()
            .value(column.getValue())
            .name(column.getName())
            .build();
  }

   Spread toDomainTypeOf(SpreadTypeOfDocument document2) {
    List<Row> rows = document2.getRows().stream()
            .map(this::toRowTypeOf)
            .collect(toList());
    return Spread.builder()
            .id(document2.getId())
            .name(document2.getName())
            .rows(rows)
            .build();
  }


//  private <T, R> T toRowDocument(Row row, BiFunction<Integer, List<R>, T> builderFunction) {
//    List<R> columns = row.getColumns().stream()
//            .map(this::toColumnDocument)
//            .collect(toList());
//    return builderFunction.apply(row.getIndex(), columns);
//  }

}
