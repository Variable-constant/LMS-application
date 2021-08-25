package com.karpenko.lms.service;

import com.karpenko.lms.domain.Role;
import com.karpenko.lms.domain.User;
import com.karpenko.lms.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();

    Optional<Role> findById(long id);

    void save(Role role);

    void deleteById(long id);
}