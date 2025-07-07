package prototype.hexa.common.port.out;

import java.util.Optional;

public interface CommandOutPort<Domain, ID> {
  Domain save(Domain domain);
  Domain update(Domain id);
  void delete(ID id);

}
