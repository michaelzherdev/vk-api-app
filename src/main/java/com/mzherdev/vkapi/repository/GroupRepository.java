package com.mzherdev.vkapi.repository;

import com.mzherdev.vkapi.model.Group;

import java.util.List;

/**
 * Created by macuser on 26.10.16.
 */
public interface GroupRepository {

    Group save(Group group);

    Group get(Long id);

    void delete(Long id);

    Group getByName(String name);

    List<Group> getAll();
}
