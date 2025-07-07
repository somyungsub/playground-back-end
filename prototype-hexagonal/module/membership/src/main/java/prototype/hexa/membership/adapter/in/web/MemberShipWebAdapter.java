package prototype.hexa.membership.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prototype.hexa.common.annotation.WebAdapter;
import prototype.hexa.common.api.ApiResponse;
import prototype.hexa.membership.application.port.in.MemberShipUseCase;
import prototype.hexa.membership.application.port.in.command.JoinMemberShip;
import prototype.hexa.membership.domain.model.Member;

@WebAdapter
@RequestMapping("/v1/membership")
@RequiredArgsConstructor
public class MemberShipWebAdapter {
  private final MemberShipUseCase memberShipUseCase;
  private final MemberWebMapper memberWebMapper;

  @GetMapping("/{id}")
  public ApiResponse<QueryResponse> fetchMember(@PathVariable long id) {
    Member member = memberShipUseCase.findById(id);
    return ApiResponse.ok(memberWebMapper.toResponseBase(member));
  }
  @GetMapping
  public ApiResponse<QueryResponse> fetchMemberByName(@RequestParam String name) {
    Member member = memberShipUseCase.findByName(name);
    return ApiResponse.ok(memberWebMapper.toResponseBase(member));
  }

  @PostMapping
  public ApiResponse<QueryResponse> joinMemberShip(@RequestBody JoinRequest joinRequest) {
    JoinMemberShip joinMemberShip = memberWebMapper.toCommand(joinRequest);
    Member save = memberShipUseCase.save(joinMemberShip);
    return ApiResponse.ok(memberWebMapper.toResponseBase(save));
  }
}
