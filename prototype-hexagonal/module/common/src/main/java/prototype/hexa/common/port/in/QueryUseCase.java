package prototype.hexa.common.port.in;

import java.util.List;

public interface QueryUseCase<Domain, ID> {
  Domain findById(ID id);
  List<Domain> findAllById(ID id);
}
