package com.guisebastiao.api.dtos;

import com.guisebastiao.api.enums.State;
import com.guisebastiao.api.models.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AddressResponseDTO {
    private UUID id;
    private String street;
    private String city;
    private State state;
    private String zip;

    public AddressResponseDTO toDto(Address address) {
        AddressResponseDTO dto = new AddressResponseDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZip(address.getZip());
        return dto;
    }
}
