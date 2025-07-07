package prototype.hexa.membership.domain.emumeration;

import lombok.Getter;
import prototype.hexa.membership.application.port.in.command.JoinMemberShip;
import prototype.hexa.membership.application.port.in.command.QueryMemberShip;
import prototype.hexa.membership.domain.model.AdultMember;
import prototype.hexa.membership.domain.model.KidsMember;
import prototype.hexa.membership.domain.model.Member;

import java.util.function.Function;

@Getter
public enum RegistrationType {
  ADULT("성인", AdultMember::withoutId, AdultMember::withId),
  KIDS("어린이", KidsMember::withoutId, KidsMember::withId)
  ;

  private final String expression;
  private final Function<JoinMemberShip, Member> createMemberFunction;
  private final Function<QueryMemberShip, Member> createMemberFunction2;
  RegistrationType(String expression, Function<JoinMemberShip, Member> createMemberFunction, Function<QueryMemberShip, Member> createMemberFunction2) {
    this.expression = expression;
    this.createMemberFunction = createMemberFunction;
    this.createMemberFunction2 = createMemberFunction2;
  }

//  public static Member createMember(JoinMemberShip joinMemberShip) {
//    return joinMemberShip.getRegistrationType().function.apply(joinMemberShip);
//  }

//  abstract Member createMember(JoinMemberShip joinMemberShip);

}
