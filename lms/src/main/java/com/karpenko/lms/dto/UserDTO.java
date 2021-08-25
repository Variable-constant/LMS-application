package com.karpenko.lms.dto;

import com.karpenko.lms.domain.Course;
import com.karpenko.lms.domain.Role;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Set<Role> roles;
}
