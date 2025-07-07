package prototype.hexa.membership.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import prototype.hexa.common.annotation.UseCase;
import prototype.hexa.common.exception.GlobalException;
import prototype.hexa.membership.application.port.in.MemberShipUseCase;
import prototype.hexa.membership.application.port.in.command.JoinMemberShip;
import prototype.hexa.membership.application.port.out.MemberShipOutPort;
import prototype.hexa.membership.domain.model.Member;

import java.util.List;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class MemberShipService implements MemberShipUseCase {
  private final MemberShipOutPort memberShipOutPort;

  @Override
  @Transactional
  public Member save(JoinMemberShip join) {
    Member member = Member.create(join);
    return Optional
            .ofNullable(memberShipOutPort.save(member))
            .orElseThrow(() -> new GlobalException("WNE-MEMBER-0001", join.getName()));
  }

  @Override
  public Member update(Long aLong) {
    return null;
  }

  @Override
  public void delete(Long aLong) {

  }

  @Override
  public Member findById(Long id) {
    return Optional
            .ofNullable(memberShipOutPort.findById(id))
            .orElseThrow(() -> new GlobalException("WNE-MEMBER-0001", id));
  }

  @Override
  public List<Member> findAllById(Long aLong) {
    return null;
  }

  @Override
  public Member findByName(String name) {
    return Optional
            .ofNullable(memberShipOutPort.findByName(name))
            .orElseThrow(() -> new GlobalException("WNE-MEMBER-0002", name));
  }
}
