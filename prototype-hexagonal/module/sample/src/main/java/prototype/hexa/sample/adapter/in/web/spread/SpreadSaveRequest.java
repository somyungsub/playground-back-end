package prototype.hexa.sample.adapter.in.web.spread;

import prototype.hexa.sample.domain.spread.vo.Row;

import java.util.List;

record SpreadSaveRequest(String name, List<Row> rows) {
}
