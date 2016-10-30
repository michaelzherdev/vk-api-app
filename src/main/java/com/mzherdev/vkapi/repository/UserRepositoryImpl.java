package com.mzherdev.vkapi.repository;

import com.mzherdev.vkapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by macuser on 26.10.16.
 */
@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {
    private static final Sort SORT_NAME = new Sort("name");

    @Autowired
    private ProxyUserRepository proxy;

    @Override
    public User save(User user) {
        return proxy.save(user);
    }

    @Override
    public User get(Long id) {
        return proxy.getOne(id);
    }

    @Override
    public List<User> getAll() {
        return proxy.findAll(SORT_NAME);
    }
}
