package prototype.hexa.membership.adapter.out.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import prototype.hexa.membership.domain.model.Member;
import prototype.hexa.membership.domain.vo.PhoneNumber;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
@Table(name = "MEMBER")
@Getter
@NoArgsConstructor
class MemberJpaEntity {
  @Id
  @GeneratedValue
  private long id;
  @Column(name = "NAME")
  private String name;
  @Column(name = "PASSWORD")
  private String password;
  @Column(name = "AGE")
  private int age;
  @Column(name = "EMAIL")
  private String email;
  @Column(name = "ADDRESS")
  private String address;
  @Column(name = "REGISTRATION_TYPE")
  private String registrationType;
  @Column(name = "PHONE_NUMBER1")
  private String phoneNumber1;
  @Column(name = "PHONE_NUMBER2")
  private String phoneNumber2;
  @Column(name = "IP")
  private String ip = "127.0.0.1";

  private MemberJpaEntity(long id, String name, String password, int age, String email, String address, String registrationType, String phoneNumber1, String phoneNumber2) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.age = age;
    this.email = email;
    this.address = address;
    this.registrationType = registrationType;
    this.phoneNumber1 = phoneNumber1;
    this.phoneNumber2 = phoneNumber2;
  }

  public static MemberJpaEntity of(Member member) {
    Map<Integer, PhoneNumber> indexMap = listToIndexMap(member.getPhoneNumbers());
    indexMap.getOrDefault(0, PhoneNumber.empty()).getFullPhoneNumber();
    return new MemberJpaEntity(
            member.getId(),
            member.getName(),
            member.getPassword(),
            member.getAge(),
            member.getFullEmailAddress(),
            member.getFullAddress(),
            member.getRegistrationType().name(),
            indexMap.getOrDefault(0, PhoneNumber.empty()).getFullPhoneNumber(),
            indexMap.getOrDefault(1, PhoneNumber.empty()).getFullPhoneNumber()
    );
  }

  private static <T> Map<Integer, T> listToIndexMap(List<T> list) {
    return IntStream.range(0, list.size())
            .boxed()
            .collect(Collectors.toMap(i -> i, list::get));
  }
}
