package prototype.hexa.common.port.out;

import java.util.List;
import java.util.Optional;

public interface QueryOutPort<Domain, ID> {
  Domain findById(ID id);
//  List<Domain> findAllById(ID id);
}
