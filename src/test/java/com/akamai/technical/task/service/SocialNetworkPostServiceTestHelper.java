package com.akamai.technical.task.service;

import com.akamai.technical.task.model.PageCriteria;
import com.akamai.technical.task.model.SocialNetworkPost;
import com.akamai.technical.task.model.dto.SocialNetworkPostDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;


public class SocialNetworkPostServiceTestHelper {
    public SocialNetworkPost buildPost(Long id, String author, String content) {
        return SocialNetworkPost
                .builder()
                .id(id)
                .postDate(LocalDate.now())
                .viewCount(0L)
                .author(author)
                .content(content)
                .build();
    }

    public SocialNetworkPostDto buildPostDto(Long id, String author, String content) {
        return SocialNetworkPostDto
                .builder()
                .id(id)
                .postDate(LocalDate.now())
                .viewCount(0L)
                .author(author)
                .content(content)
                .build();
    }

    public SocialNetworkPostDto buildPostDto(SocialNetworkPost post) {
        return buildPostDto(post.getId(), post.getAuthor(), post.getContent());
    }

    public PageRequest createPageRequest() {
        return PageRequest.of(
                0,
                2,
                Sort.Direction.DESC,
                "viewCount"
        );
    }

    public PageCriteria createPageCriteria() {
        return new PageCriteria(
                0,
                2,
                "viewCount",
                false
        );
    }
}