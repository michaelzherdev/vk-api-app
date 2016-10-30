package com.mzherdev.vkapi.service;

import com.mzherdev.vkapi.model.User;
import com.mzherdev.vkapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by macuser on 26.10.16.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public User get(Long id) {
        return repository.get(id);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public void update(User user) {
        repository.save(user);
    }
}
