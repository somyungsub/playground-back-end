package prototype.hexa.sample.adapter.out.mongo.spread;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;
import prototype.hexa.common.annotation.PersistenceAdapter;
import prototype.hexa.sample.application.port.out.spread.SpreadOutPort;
import prototype.hexa.sample.application.port.out.spread.SpreadTypeOfOutPort;
import prototype.hexa.sample.domain.spread.Spread;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
class SpreadPersistence implements SpreadOutPort, SpreadTypeOfOutPort {
  private final SpreadMongoRepository spreadMongoRepository;
  private final SpreadMongoReactiveRepository spreadMongoReactiveRepository;

  private final SpreadMongoTypeOfRepository spreadMongoTypeOfRepository;
  private final RowMongoTypeOfRepository rowMongoTypeOfRepository;
  private final ColumnMongoTypeOfRepository columnMongoTypeOfRepository;
  private final SpreadMongoTypeOfReactiveRepository spreadMongoTypeOfReactiveRepository;

  private final SpreadOutPortMapper spreadOutPortMapper;
  private final MongoTemplate mongoTemplate;

  @Override
  @Transactional(transactionManager = "mongoTransactionManager")
  public Spread save(Spread spread) {
    SpreadDocument spreadDocument = spreadOutPortMapper.toDocument(spread);
    SpreadDocument result = spreadMongoRepository.save(spreadDocument);
    return spreadOutPortMapper.toDomain(result);
  }

  @Override
  @Transactional(transactionManager = "mongoTransactionManager")
  public Spread saveTemplate(Spread spread) {
    SpreadDocument spreadDocument = spreadOutPortMapper.toDocument(spread);
    SpreadDocument result = mongoTemplate.insert(spreadDocument);
    return spreadOutPortMapper.toDomain(result);
  }

  @Override
  public Spread findByName(String name) {
    return spreadMongoRepository
            .findByName(name)
            .map(spreadOutPortMapper::toDomain)
            .orElse(null);
  }

  @Override
  public Mono<Spread> asyncFindByName(String name) {
    return spreadMongoReactiveRepository
            .findByName(name)
            .map(spreadOutPortMapper::toDomain);
  }

  @Override
  @Transactional(transactionManager = "mongoTransactionManager")
  public Spread saveSpreadTyeOf(Spread spread) {
    SpreadTypeOfDocument spreadTypeOfDocument = save2(spread);
    return spreadOutPortMapper.toDomainTypeOf(spreadTypeOfDocument);
  }

  private SpreadTypeOfDocument save2(Spread spread) {
    List<RowTypeOfDocument> list = new ArrayList<>();

    spread.getRows().forEach(row -> {
      List<ColumnTypeOfDocument> columns = row.getColumns()
              .stream()
              .map(spreadOutPortMapper::toColumnDocumentTypeOf)
              .toList();
      List<ColumnTypeOfDocument> columnDocuments = columnMongoTypeOfRepository.saveAll(columns);// Save columns first

      RowTypeOfDocument rows = RowTypeOfDocument.builder()
              .index(row.getIndex())
              .columns(columnDocuments) // Ensure columns are saved before this step
              .build();
      RowTypeOfDocument saveRow = rowMongoTypeOfRepository.save(rows);// Save each row with its columns

      list.add(saveRow);
    });

    SpreadTypeOfDocument spreadDocument = SpreadTypeOfDocument.builder()
            .rows(list)
            .name(spread.getName())
            .build();

    return spreadMongoTypeOfRepository.save(spreadDocument);
  }

  @Override
  public Spread findByNameTypeOf(String name) {
    return spreadMongoTypeOfRepository
            .findByName(name)
            .map(spreadOutPortMapper::toDomainTypeOf)
            .orElse(null);
  }

  @Override
  public Mono<Spread> asyncTypeOfFindByName(String name) {
    return spreadMongoTypeOfReactiveRepository
            .findByName(name)
            .map(spreadOutPortMapper::toDomainTypeOf);
  }
}
