package prototype.hexa.membership.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import prototype.hexa.common.annotation.PersistenceAdapter;
import prototype.hexa.membership.application.port.out.MemberShipOutPort;
import prototype.hexa.membership.domain.model.Member;

@PersistenceAdapter
@RequiredArgsConstructor
class MemberShipJpaAdapter implements MemberShipOutPort {
  private final MemberShipRepository memberShipRepository;
  private final MemberShipOutMapper memberShipOutMapper;
  @Override
  public Member save(Member member) {
    MemberJpaEntity entity = memberShipOutMapper.toEntity(member);
    MemberJpaEntity save = memberShipRepository.save(entity);
    return memberShipOutMapper.toDomain(save);
  }

  @Override
  public Member update(Member id) {
    return null;
  }

  @Override
  public void delete(Long aLong) {

  }

  @Override
  public Member findById(Long id) {
    return memberShipRepository.findById(id)
            .map(memberShipOutMapper::toDomain)
            .orElse(null);
  }

  @Override
  public Member findByName(String name) {
    return memberShipRepository.findByName(name)
            .map(memberShipOutMapper::toDomain)
            .orElse(null);
  }
}
