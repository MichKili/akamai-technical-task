package com.akamai.technical.task.service;

import com.akamai.technical.task.exception.SocialNetworkPostNotFoundException;
import com.akamai.technical.task.model.PageCriteria;
import com.akamai.technical.task.model.SocialNetworkPost;
import com.akamai.technical.task.model.SocialNetworkPostInput;
import com.akamai.technical.task.model.dto.SocialNetworkPostDto;
import com.akamai.technical.task.repository.SocialNetworkPostRepository;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.akamai.technical.task.util.ValidationUtil.checkSortProperty;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SocialNetworkPostServiceImpl implements SocialNetworkPostService {

    private final SocialNetworkPostRepository socialNetworkPostRepository;
    private final Mapper mapper;


    @Override
    public SocialNetworkPostDto getPostsById(Long id) {
        SocialNetworkPost socialNetworkPost = getIfPostExists(id);
        return mapper.map(socialNetworkPost, SocialNetworkPostDto.class);
    }

    @Override
    public List<SocialNetworkPostDto> getPosts(PageCriteria pageCriteria) {
        checkSortProperty(pageCriteria.sortProperty(), SocialNetworkPost.class);
        PageRequest pageRequest = createPageRequest(pageCriteria);
        Page<SocialNetworkPost> posts = socialNetworkPostRepository.findAll(pageRequest);
        return convertPageToDtoList(posts);
    }

    private List<SocialNetworkPostDto> convertPageToDtoList(Page<SocialNetworkPost> posts) {
        return posts
                .stream()
                .map(e -> mapper.map(e, SocialNetworkPostDto.class))
                .toList();
    }

    private static PageRequest createPageRequest(PageCriteria pageCriteria) {
        return PageRequest.of(
                pageCriteria.pageNumber(),
                pageCriteria.pageSize(),
                pageCriteria.ascending() ? Sort.Direction.ASC : Sort.Direction.DESC,
                pageCriteria.sortProperty()
        );
    }

    @Override
    @Transactional
    public Long createPost(SocialNetworkPostInput input) {
        SocialNetworkPost socialNetworkPost = buildSocialNetworkPost(input);
        SocialNetworkPost save = socialNetworkPostRepository.save(socialNetworkPost);
        log.debug("Post has been successfully created with id: " + save.getId());
        return save.getId();
    }

    private SocialNetworkPost buildSocialNetworkPost(SocialNetworkPostInput input) {
        return SocialNetworkPost
                .builder()
                .content(input.content())
                .author(input.content())
                .viewCount(0L)
                .postDate(LocalDate.now())
                .build();
    }

    @Override
    @Transactional
    public void updatePost(Long id, SocialNetworkPostDto input) {
        SocialNetworkPost post = getIfPostExists(id);
        input.setId(post.getId());
        SocialNetworkPost updatedPost = mapper.map(input, SocialNetworkPost.class);
        socialNetworkPostRepository.save(updatedPost);
        log.debug("Post has been successfully updated with id: " + updatedPost.getId());

    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        SocialNetworkPost post = getIfPostExists(id);
        socialNetworkPostRepository.deleteById(post.getId());
        log.debug("Post has been successfully deleted with id: " + post.getId());

    }

    private SocialNetworkPost getIfPostExists(Long id) {
        return socialNetworkPostRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.debug("Not found Post with given id: " + id);
                    return new SocialNetworkPostNotFoundException("Not found Post with given id: " + id);
                });
    }
}
