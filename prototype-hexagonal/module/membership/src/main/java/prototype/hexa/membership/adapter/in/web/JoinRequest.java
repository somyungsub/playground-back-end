package prototype.hexa.membership.adapter.in.web;

import prototype.hexa.membership.domain.emumeration.RegistrationType;
import prototype.hexa.membership.domain.vo.Address;
import prototype.hexa.membership.domain.vo.Email;
import prototype.hexa.membership.domain.vo.PhoneNumber;

import java.util.List;

record JoinRequest(
        String name,
        String password,
        int age,
        Email email,
        Address address,
        RegistrationType registrationType,
        List<PhoneNumber>phoneNumbers
) {
}
