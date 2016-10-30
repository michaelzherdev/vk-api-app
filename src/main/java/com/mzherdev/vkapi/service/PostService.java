package com.mzherdev.vkapi.service;

import com.mzherdev.vkapi.model.Post;

import java.util.List;

/**
 * Created by macuser on 27.10.16.
 */
public interface PostService {

    Post save(Post post, Long groupId);

    Post get(Long id);

    List<Post> getAll();
}
