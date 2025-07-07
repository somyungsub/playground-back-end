package prototype.hexa.membership.domain.service;

import prototype.hexa.membership.application.port.in.command.JoinMemberShip;
import prototype.hexa.membership.domain.model.Member;

public class MemberManager {
  public static Member createMember(JoinMemberShip joinMemberShip) {
    return null;
//    RegistrationType registrationType = joinMemberShip.getRegistrationType().createMember(joinMemberShip);
//    return registrationType.createMember(joinMemberShip);
//    if (registrationType == RegistrationType.ADULT) {
//      return new AdultMember(joinMemberShip);
//    } else {
//      return new KidsMember(joinMemberShip);
//    }
  }
}
