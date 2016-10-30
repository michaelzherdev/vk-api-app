package com.mzherdev.vkapi.repository;

import com.mzherdev.vkapi.model.Post;

import java.util.List;

/**
 * Created by macuser on 27.10.16.
 */
public interface PostRepository {

    Post save(Post post, Long groupId);

    Post get(Long id);

    List<Post> getAll();
}
