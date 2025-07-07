package prototype.hexa.membership.adapter.in.web;

import org.springframework.stereotype.Component;
import prototype.hexa.membership.application.port.in.command.JoinMemberShip;
import prototype.hexa.membership.domain.model.Member;

@Component
class MemberWebMapper {
  JoinMemberShip toCommand(JoinRequest request) {
    return JoinMemberShip.builder()
            .age(request.age())
            .name(request.name())
            .password(request.password())
            .email(request.email())
            .address(request.address())
            .registrationType(request.registrationType())
            .phoneNumbers(request.phoneNumbers())
            .build()
            ;
  }

  Member toResponse(Member member) {
    return member;
  }

  QueryResponse toResponseBase(Member member) {
    return QueryResponse.builder()
            .age(member.getAge())
            .name(member.getName())
            .address(member.getAddress())
            .registrationType(member.getRegistrationType())
            .email(member.getEmail())
            .phoneNumbers(member.getPhoneNumbers())
            .build();
  }
}
