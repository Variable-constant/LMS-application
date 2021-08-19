package com.karpenko.lms.service;

import com.karpenko.lms.domain.User;
import com.karpenko.lms.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> findAll();

    Optional<UserDTO> findById(long id);

    void save(UserDTO userDTO);

    void deleteById(long id);

    List<User> findUsersNotAssignedToCourse(long id);

    Optional<User> findUserByUsername(String username);

    List<User> findByNameWithPrefix(String prefix);
}
