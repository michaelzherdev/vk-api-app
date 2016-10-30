package com.mzherdev.vkapi.repository;

import com.mzherdev.vkapi.model.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by macuser on 27.10.16.
 */
@Transactional(readOnly = true)
public interface ProxyPostRepository extends JpaRepository<Post, Long> {

    @Override
    @Transactional
    Post save(Post post);

    @Override
    Post getOne(Long id);

    @Override
    List<Post> findAll(Sort sort);
}
