package com.zionique.userserviceapplication.dtos;

import com.zionique.userserviceapplication.models.Role;
import com.zionique.userserviceapplication.models.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String email;
    private String name;
    private Set<Role> roles = new HashSet<>();

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
//    @ManyToMany(fetch = FetchType.EAGER)
//    private List<Role> roles;
}
