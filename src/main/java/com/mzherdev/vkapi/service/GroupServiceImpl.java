package com.mzherdev.vkapi.service;

import com.mzherdev.vkapi.model.Group;
import com.mzherdev.vkapi.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by macuser on 26.10.16.
 */

@Service("groupService")
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository repository;

    public Group save(Group group) {
        return repository.save(group);
    }

    public Group get(Long id) {
        return repository.get(id);
    }

    public Group getByName(String name) {
        return repository.getByName(name);
    }

    public List<Group> getAll() {
        return repository.getAll();
    }

    public void update(Group group) {
        repository.save(group);
    }

    public void delete(Long groupId) {
        repository.delete(groupId);
    }
}
