package prototype.hexa.asynctest.repository.sample;


import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.jdbc.core.JdbcTemplate;
import prototype.hexa.asynctest.domain.Sample;
import prototype.hexa.asynctest.repository.SampleRepository;
import prototype.hexa.common.annotation.PersistenceAdapter;

import static org.jooq.impl.DSL.*;
import static prototype.hexa.asynctest.Tables.SAMPLE_;
import static prototype.hexa.asynctest.Tables.SAMPLE_INPUT;


@PersistenceAdapter
@RequiredArgsConstructor
class SampleJdbcAdapter implements SampleRepository {
  private final SampleJdbcRepository sampleJdbcRepository;
  private final SampleMapper sampleMapper;
  private final DSLContext dsl;
  private final JdbcTemplate jdbcTemplate;

  @Override
  public Sample findById(long id) {
    return sampleJdbcRepository.findById(id)
      .map(sampleMapper::toDomain)
      .orElse(null);
  }

//  @Override
//  public Sample joinSample(long sampleId) {
//
//    Result<Record4<Long, String, String, Result<Record3<Long, String, String>>>> inputs = dsl
//      .select(SAMPLE_.ID, SAMPLE_.NAME, SAMPLE_.CODE,
//        multiset(
//          select(SAMPLE_INPUT.ID, SAMPLE_INPUT.INPUT_NAME, SAMPLE_INPUT.INPUT_VALUE)
//            .from(SAMPLE_INPUT)
//            .where(SAMPLE_INPUT.SAMPLE_ID.eq(SAMPLE_.ID))
//        ).as("inputs"))
//      .from(SAMPLE_)
//      .fetch();
//
//    return sampleMapper.toDomain(fetch);
//  }
  @Override
  public Sample joinSample(long sampleId) {

    Result<Record> fetch = dsl
      .select()
      .from(SAMPLE_)
      .join(SAMPLE_INPUT)
      .on(SAMPLE_.ID.eq(SAMPLE_INPUT.SAMPLE_ID))
      .fetch();
    return sampleMapper.toDomain(fetch);
  }

  @Override
  public void createTable(long id) {

    // Step 1: 테이블이 존재하는지 확인
//    boolean tableExists = dsl.fetchExists(
//      dsl.selectFrom("information_schema.tables")
//        .where(DSL.field("table_schema").eq("sample_schema"))
//        .and(DSL.field("table_name").eq("sample_mview"))
//    );


    // Step 1: Drop table if exists
    dsl.dropTableIfExists(name("sample", "sample_mview")).execute();

    // Step 2: Define source table with schema
    Table<?> sampleTable = table(name("sample", "sample"));

    // Step 3: Create the target table with data selection
    dsl.createTable(name("sample", "sample_mview"))
      .as(
        dsl.select(field(name("id")), field(name("name")))
          .from(sampleTable)
          .where(field(name("id")).eq(id))
      )
      .execute();

    // Step 4: Define table and field for indexing
    Table<?> sampleMviewTable = table(name("sample", "sample_mview"));
    Field<String> nameField = sampleMviewTable.field(name("name"), String.class);

    // Step 5: Add primary key constraint
    dsl.alterTable(sampleMviewTable)
      .add(DSL.constraint("pk_sample_mview").primaryKey(field(name("id"))))
      .execute();

    // Step 6: Create an index on the 'name' column
//    dsl.createIndex("idx_sample_mview_name")
//      .on(sampleMviewTable, nameField)
//      .execute();

  }

  @Override
  public Sample findCtas(long id) {
    String sql = "select * from sample.sample_mview where id = ?";
    return jdbcTemplate.queryForObject(
      sql,
      (rs, rowNum) -> Sample.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("name"))
        .build(),
      id
    );
  }
}
