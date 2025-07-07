package prototype.hexa.membership.domain.model;

import prototype.hexa.membership.application.port.in.command.JoinMemberShip;
import prototype.hexa.membership.application.port.in.command.QueryMemberShip;
import prototype.hexa.membership.domain.emumeration.RegistrationType;
import prototype.hexa.membership.domain.vo.Address;
import prototype.hexa.membership.domain.vo.Email;
import prototype.hexa.membership.domain.vo.PhoneNumber;

import java.util.List;

public class KidsMember extends Member{
  public KidsMember(long id, String name, String password, int age, Email email, Address address, RegistrationType registrationType, List<PhoneNumber> phoneNumbers) {
    super(id, name, password, age, email, address, registrationType, phoneNumbers);
  }

  private KidsMember(JoinMemberShip joinMemberShip) {
    super(joinMemberShip);
  }
  private KidsMember(QueryMemberShip queryMemberShip) {
    super(queryMemberShip);
  }

  public static Member withoutId(JoinMemberShip join) {
    return new KidsMember(join);
//    return withoutIdMember(join);
  }
  public static Member withId(QueryMemberShip query) {
    return new KidsMember(query);
//    return withoutIdMember(join);
  }

  @Override
  void validateMemberShip() {

  }

  @Override
  void submitMemberShipReview() {

  }
}
