package com.mzherdev.vkapi.service;

import com.mzherdev.vkapi.model.Post;
import com.mzherdev.vkapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by macuser on 27.10.16.
 */
@Service("postService")
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public Post save(Post post, Long groupId) {
        return postRepository.save(post, groupId);
    }

    @Override
    public Post get(Long id) {
        return postRepository.get(id);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.getAll();
    }
}
