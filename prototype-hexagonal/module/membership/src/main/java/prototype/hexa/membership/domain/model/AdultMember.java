package prototype.hexa.membership.domain.model;

import prototype.hexa.membership.application.port.in.command.JoinMemberShip;
import prototype.hexa.membership.application.port.in.command.QueryMemberShip;
import prototype.hexa.membership.domain.emumeration.RegistrationType;
import prototype.hexa.membership.domain.vo.Address;
import prototype.hexa.membership.domain.vo.Email;
import prototype.hexa.membership.domain.vo.PhoneNumber;

import java.util.List;

public class AdultMember extends Member {

  public AdultMember(long id, String name, String password, int age, Email email, Address address, RegistrationType registrationType, List<PhoneNumber> phoneNumbers) {
    super(id, name, password, age, email, address, registrationType, phoneNumbers);
  }

  private AdultMember(JoinMemberShip join) {
    super(join);
  }
  private AdultMember(QueryMemberShip query) {
    super(query);
  }

  public static Member withoutId(JoinMemberShip join) {
    return new AdultMember(join);
//    return withoutIdMember(join);
  }

  public static Member withId(QueryMemberShip query) {
    return new AdultMember(query);
//    return withoutIdMember(join);
  }


  @Override
  void validateMemberShip() {

  }

  @Override
  void submitMemberShipReview() {

  }
}
