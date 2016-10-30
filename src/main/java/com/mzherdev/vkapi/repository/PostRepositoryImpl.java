package com.mzherdev.vkapi.repository;

import com.mzherdev.vkapi.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by macuser on 27.10.16.
 */
@Repository("postRepository")
public class PostRepositoryImpl implements PostRepository {

    private static final Sort SORT_NAME = new Sort("date");

    @Autowired
    private ProxyPostRepository proxy;

    @Autowired
    private ProxyGroupRepository proxyGroupRepository;

    @Override
    public Post save(Post post, Long groupId) {
        if (!post.isNew() && get(post.getId()) == null) {
            return null;
        }
        post.setGroup(proxyGroupRepository.getOne(groupId));
        return proxy.save(post);
    }

    @Override
    public Post get(Long id) {
        return proxy.getOne(id);
    }

    @Override
    public List<Post> getAll() {
        return proxy.findAll(SORT_NAME);
    }
}
