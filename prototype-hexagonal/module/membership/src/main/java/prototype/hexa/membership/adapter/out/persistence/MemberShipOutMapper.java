package prototype.hexa.membership.adapter.out.persistence;

import org.springframework.stereotype.Component;
import prototype.hexa.membership.domain.emumeration.RegistrationType;
import prototype.hexa.membership.domain.model.Member;
import prototype.hexa.membership.domain.vo.Address;
import prototype.hexa.membership.domain.vo.Email;
import prototype.hexa.membership.domain.vo.PhoneNumber;

import java.util.List;

@Component
class MemberShipOutMapper {
  MemberJpaEntity toEntity(Member member) {
    return MemberJpaEntity.of(member);
  }
  //TODO 정리
  Member toDomain(MemberJpaEntity entity) {
//    private final long id;
//    private final String name;
//    private final String password;
//    private final int age;
//    @Delegate
//    private final Email email;
//    @Delegate
//    private final Address address;
//    @Delegate
//    private final RegistrationType registrationType;
//    @Delegate
//    private final List<PhoneNumber> phoneNumbers;
    return Member.createFrom(
            entity.getId(),
            entity.getName(),
            entity.getPassword(),
            entity.getAge(),
            Email.of(entity.getEmail()),
            Address.of(entity.getAddress()),
            RegistrationType.valueOf(entity.getRegistrationType()),
            List.of(
                    PhoneNumber.of(entity.getPhoneNumber1()),
                    PhoneNumber.of(entity.getPhoneNumber2())
            )
    );
  }
}
