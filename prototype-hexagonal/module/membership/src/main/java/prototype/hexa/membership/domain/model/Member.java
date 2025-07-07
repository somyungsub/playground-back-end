package prototype.hexa.membership.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import prototype.hexa.membership.application.port.in.command.JoinMemberShip;
import prototype.hexa.membership.application.port.in.command.QueryMemberShip;
import prototype.hexa.membership.domain.emumeration.RegistrationType;
import prototype.hexa.membership.domain.vo.Address;
import prototype.hexa.membership.domain.vo.Email;
import prototype.hexa.membership.domain.vo.PhoneNumber;

import java.util.List;

@AllArgsConstructor
@Getter
public abstract class Member {
  private final long id;
  private final String name;
  private final String password;
  private final int age;
  private final Email email;
  private final Address address;
  private final RegistrationType registrationType;
  private final List<PhoneNumber> phoneNumbers;

  public Member(JoinMemberShip join) {
    this.id = 0L;
    this.name = join.getName();
    this.password = join.getPassword();
    this.age = join.getAge();
    this.email = join.getEmail();
    this.address = join.getAddress();
    this.registrationType = join.getRegistrationType();
    this.phoneNumbers = join.getPhoneNumbers();
  }
  public Member(QueryMemberShip query) {
    this.id = query.getId();
    this.name = query.getName();
    this.password = query.getPassword();
    this.age = query.getAge();
    this.email = query.getEmail();
    this.address = query.getAddress();
    this.registrationType = query.getRegistrationType();
    this.phoneNumbers = query.getPhoneNumbers();
  }

  public static Member create(JoinMemberShip joinMemberShip) {
    Member member = joinMemberShip.createMember();
    member.validateMemberShip();
    member.submitMemberShipReview();
    return member;
  }

  public static Member createFrom(long id, String name, String password, int age, Email email, Address address, RegistrationType registrationType, List<PhoneNumber> phoneNumbers) {
    QueryMemberShip query = QueryMemberShip.builder()
            .id(id)
            .name(name)
            .password(password)
            .age(age)
            .email(email)
            .address(address)
            .registrationType(registrationType)
            .phoneNumbers(phoneNumbers)
            .build();

    // TODO 통합
    return  registrationType
            .getCreateMemberFunction2()
            .apply(query);
  }

//  private static Member withoutId() {
//  }

//  protected abstract Member withoutId(JoinMemberShip join);
//  protected static Member withoutIdMember(JoinMemberShip join) {
//    return builder()
//      .id(0L)
//      .name(join.getName())
//      .password(join.getPassword())
//      .age(join.getAge())
//      .email(join.getEmail())
//      .address(join.getAddress())
//      .registrationType(join.getRegistrationType())
//      .phoneNumbers(join.getPhoneNumbers())
//      .build();
//  }

  // 유효성검증 회원
  abstract void validateMemberShip();
  // 회원심사요청
  abstract void submitMemberShipReview();

  public String getFullEmailAddress() {
    return email.getFullEmailAddress();
  }

  public String getFullAddress() {
    return address.getFullAddress();
  }
}
