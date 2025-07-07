package prototype.hexa.membership.application.port.in.command;

import lombok.Builder;
import lombok.Value;
import prototype.hexa.membership.domain.emumeration.RegistrationType;
import prototype.hexa.membership.domain.model.Member;
import prototype.hexa.membership.domain.vo.Address;
import prototype.hexa.membership.domain.vo.Email;
import prototype.hexa.membership.domain.vo.PhoneNumber;

import java.util.List;

@Value
@Builder
public class JoinMemberShip {
  String name;
  String password;
  int age;
  Email email;
  Address address;
  RegistrationType registrationType;
  List<PhoneNumber> phoneNumbers;

  public Member createMember() {
    return registrationType.getCreateMemberFunction().apply(this);
  }
//  public Member createMember() {
//    return registrationType.createMember(this);
//  }
}
