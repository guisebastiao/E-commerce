package com.guisebastiao.api.dtos;

import com.guisebastiao.api.enums.Role;
import com.guisebastiao.api.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String email;
    private String phone;
    private Role role;

    public UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        return dto;
    }
}
