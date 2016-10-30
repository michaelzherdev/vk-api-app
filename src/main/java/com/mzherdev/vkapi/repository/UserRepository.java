package com.mzherdev.vkapi.repository;

import com.mzherdev.vkapi.model.User;

import java.util.List;

/**
 * Created by macuser on 26.10.16.
 */
public interface UserRepository {

    User save(User user);

    User get(Long id);

    List<User> getAll();
}
