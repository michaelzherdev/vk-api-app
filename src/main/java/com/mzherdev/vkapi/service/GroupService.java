package com.mzherdev.vkapi.service;

import com.mzherdev.vkapi.model.Group;

import java.util.List;

/**
 * Created by macuser on 26.10.16.
 */
public interface GroupService {

    Group save(Group group);

    Group get(Long id);

    Group getByName(String name);

    List<Group> getAll();

    void update(Group group);

    void delete(Long groupId);
}
