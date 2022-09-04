package com.blogging.payloads;

import com.blogging.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;

    @NotEmpty(message = "Can not be empty")
    @Size(min = 4 , message ="Username must have atleast 4 characters")
    private String name;

    @Email(message="Invalid Email")
    private String email;

    @NotEmpty(message = "Can not be empty")
    @Size(min = 3, max=10 , message ="Password must be atleast of 3 char and maximum of 10")
    private String password;

    @NotEmpty(message = "Can not be empty")
    private String about;

    private Set<RoleDto> roles = new HashSet<>();
}
