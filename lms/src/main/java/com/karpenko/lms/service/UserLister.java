package com.karpenko.lms.service;

import com.karpenko.lms.dao.LessonRepository;
import com.karpenko.lms.dao.UserRepository;
import com.karpenko.lms.domain.Lesson;
import com.karpenko.lms.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UserLister {
    private final UserRepository repository;

    @Autowired
    public UserLister(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public void save(User user) {
        repository.save(user);
    }

    public void delete(Long id) {
        repository.delete(repository.getById(id));
    }

    @PostConstruct
    public void init() {
        if (repository.findAll().isEmpty()) {
            save(new User(null, "Рик", null));
            save(new User(null, "Морти", null));
        }
    }
}
