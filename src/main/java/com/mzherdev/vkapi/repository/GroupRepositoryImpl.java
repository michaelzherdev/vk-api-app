package com.mzherdev.vkapi.repository;

import com.mzherdev.vkapi.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by macuser on 27.10.16.
 */
@Repository("groupRepository")
public class GroupRepositoryImpl implements GroupRepository {
    private static final Sort SORT_NAME = new Sort("name");

    @Autowired
    private ProxyGroupRepository proxy;

    @Override
    public Group save(Group group) {
        return proxy.save(group);
    }

    @Override
    public Group get(Long id) {
        return proxy.getOne(id);
    }

    @Override
    public void delete(Long id) {
        proxy.delete(id);
    }

    @Override
    public Group getByName(String name) {
        return null;
    }

    @Override
    public List<Group> getAll() {
        return proxy.findAll(SORT_NAME);
    }
}
