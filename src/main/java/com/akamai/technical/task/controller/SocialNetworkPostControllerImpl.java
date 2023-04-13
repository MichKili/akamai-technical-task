package com.akamai.technical.task.controller;

import com.akamai.technical.task.model.PageCriteria;
import com.akamai.technical.task.model.SocialNetworkPostInput;
import com.akamai.technical.task.model.dto.SocialNetworkPostDto;
import com.akamai.technical.task.service.SocialNetworkPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SocialNetworkPostControllerImpl implements SocialNetworkPostController {
    private final SocialNetworkPostService socialNetworkPostService;

    @Override
    public SocialNetworkPostDto getPostById(Long id) {
        return socialNetworkPostService.getPostsById(id);
    }

    @Override
    public List<SocialNetworkPostDto> getPosts(PageCriteria pageCriteria) {
        return socialNetworkPostService.getPosts(pageCriteria);
    }

    @Override
    public Long createPost(SocialNetworkPostInput input) {
        return socialNetworkPostService.createPost(input);
    }

    @Override
    public void updatePost(Long id, SocialNetworkPostDto input) {
        socialNetworkPostService.updatePost(id, input);
    }

    @Override
    public void deletePost(Long id) {
        socialNetworkPostService.deletePost(id);
    }
}
