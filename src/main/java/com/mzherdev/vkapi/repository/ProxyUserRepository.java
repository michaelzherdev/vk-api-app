package com.mzherdev.vkapi.repository;

import com.mzherdev.vkapi.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by macuser on 26.10.16.
 */
@Transactional(readOnly = true)
public interface ProxyUserRepository extends JpaRepository<User, Long> {

    @Override
    @Transactional
    User save(User user);

    @Override
    User getOne(Long id);

    @Override
    List<User> findAll(Sort sort);

    @Override
    void delete(Long id);
}