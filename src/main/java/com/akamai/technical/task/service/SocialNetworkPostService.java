package com.akamai.technical.task.service;

import com.akamai.technical.task.model.PageCriteria;
import com.akamai.technical.task.model.SocialNetworkPostInput;
import com.akamai.technical.task.model.dto.SocialNetworkPostDto;

import java.util.List;

public interface SocialNetworkPostService {
    SocialNetworkPostDto getPostsById(Long id);

    List<SocialNetworkPostDto> getPosts(PageCriteria pageCriteria);

    Long createPost(SocialNetworkPostInput input);

    void updatePost(Long id, SocialNetworkPostDto input);

    void deletePost(Long id);

}
