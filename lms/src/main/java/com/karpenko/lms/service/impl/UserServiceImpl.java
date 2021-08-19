package com.karpenko.lms.service.impl;

import com.karpenko.lms.dao.UserRepository;
import com.karpenko.lms.domain.Course;
import com.karpenko.lms.domain.User;
import com.karpenko.lms.dto.UserDTO;
import com.karpenko.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(usr -> new UserDTO(usr.getId(), usr.getUsername(), "", usr.getRoles()))
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(long id) {
        return userRepository.findById(id)
                .map(usr -> new UserDTO(usr.getId(), usr.getUsername(), "", usr.getRoles()));
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findUsersNotAssignedToCourse(long id) {
        return userRepository.findUsersNotAssignedToCourse(id);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<User> findByNameWithPrefix(String prefix) {
        return userRepository.findByUsernameLike(prefix.toLowerCase() + "%");
    }

    public void save(UserDTO userDTO) {
        userRepository.save(new User(encoder.encode(userDTO.getPassword()),
                userDTO.getId(),
                userDTO.getUsername(),
                new HashSet<>(),
                userDTO.getRoles()
        ));
    }


}
