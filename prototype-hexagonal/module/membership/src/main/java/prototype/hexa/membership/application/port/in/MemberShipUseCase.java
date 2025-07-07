package prototype.hexa.membership.application.port.in;

import prototype.hexa.common.port.in.CommandUseCase;
import prototype.hexa.common.port.in.QueryUseCase;
import prototype.hexa.membership.application.port.in.command.JoinMemberShip;
import prototype.hexa.membership.domain.model.Member;

public interface MemberShipUseCase extends CommandUseCase<Member, JoinMemberShip, Long>, QueryUseCase<Member, Long> {
  Member findByName(String name);
//  Member createMemberShip(JoinMemberShip createMemberShip);
}
