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

    public List<AddressResponseDTO> toDto(List<Address> addresses) {
        AddressResponseDTO dto = new AddressResponseDTO();

        return (List<AddressResponseDTO>) addresses.stream().map(e -> {
            dto.setId(e.getId());
            dto.setStreet(e.getStreet());
            dto.setCity(e.getCity());
            dto.setState(e.getState());
            dto.setZip(e.getZip());
            return dto;
        });
    }
}
