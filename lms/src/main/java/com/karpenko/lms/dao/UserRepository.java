package com.karpenko.lms.dao;

import com.karpenko.lms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
