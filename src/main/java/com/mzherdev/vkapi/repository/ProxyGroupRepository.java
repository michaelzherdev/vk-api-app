package com.mzherdev.vkapi.repository;

import com.mzherdev.vkapi.model.Group;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by macuser on 27.10.16.
 */
@Transactional(readOnly = true)
public interface ProxyGroupRepository extends JpaRepository<Group, Long> {

    @Override
    @Transactional
    Group save(Group group);

    @Override
    Group getOne(Long id);

    @Override
    List<Group> findAll(Sort sort);
}
