package prototype.hexa.membership.application.port.out;

import prototype.hexa.common.port.out.CommandOutPort;
import prototype.hexa.common.port.out.QueryOutPort;
import prototype.hexa.membership.domain.model.Member;

import java.util.Optional;

public interface MemberShipOutPort extends CommandOutPort<Member, Long>, QueryOutPort<Member, Long> {
  Member findByName(String name);
}
