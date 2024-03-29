package com.akamai.technical.task.service;

import com.akamai.technical.task.exception.SocialNetworkPostNotFoundException;
import com.akamai.technical.task.model.PageCriteria;
import com.akamai.technical.task.model.SocialNetworkPost;
import com.akamai.technical.task.model.SocialNetworkPostInput;
import com.akamai.technical.task.model.dto.SocialNetworkPostDto;
import com.akamai.technical.task.repository.SocialNetworkPostRepository;
import com.github.dozermapper.core.Mapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@ActiveProfiles("dev")
public class SocialNetworkPostServiceImplTest {

    public static final long ID = 1L;
    private static final long ID2 = 2L;
    public static final String AUTHOR_MICHAL = "Michal";
    public static final String AUTHOR_MAREK = "Marek";
    public static final String TEST_CONTENT = "Test Post";
    @Mock
    private SocialNetworkPostRepository repository;
    @Mock
    private Mapper mapper;
    @InjectMocks
    private SocialNetworkPostServiceImpl service;
    @BeforeMethod
    public void setUp() {
        initMocks(this);
    }

    @Test
    void should_get_post_with_correctly_mapped_values_and_invoke_mandatory_services() {
        //given
        SocialNetworkPost post = buildPost(ID, AUTHOR_MICHAL);
        SocialNetworkPostDto expectedPostDto = buildPostDto(ID);
        given(repository.findById(ID)).willReturn(Optional.of(post));
        given(mapper.map(refEq(post), eq(SocialNetworkPostDto.class))).willReturn(expectedPostDto);

        //when
        SocialNetworkPostDto actualPostDto = service.getPostsById(ID);

        //then
        SoftAssertions.assertSoftly(assertions -> {
            assertions.assertThat(actualPostDto.getPostDate()).isEqualTo(expectedPostDto.getPostDate());
            assertions.assertThat(actualPostDto.getVersion()).isEqualTo(expectedPostDto.getVersion());
            assertions.assertThat(actualPostDto.getAuthor()).isEqualTo(expectedPostDto.getAuthor());
            assertions.assertThat(actualPostDto.getContent()).isEqualTo(expectedPostDto.getContent());
            assertions.assertThat(actualPostDto.getId()).isEqualTo(expectedPostDto.getId());
            assertions.assertThat(actualPostDto.getViewCount()).isEqualTo(expectedPostDto.getViewCount());
            assertions.assertThat(actualPostDto.getIdList()).isEqualTo(expectedPostDto.getIdList());
        });
        verify(repository, Mockito.atMostOnce()).findById(ID);
        verify(mapper, Mockito.atMostOnce()).map(any(), eq(SocialNetworkPostDto.class));
    }
    @Test(expectedExceptions = SocialNetworkPostNotFoundException.class)
    void should_throw_exception_when_not_found_post_during_fetching_by_id() {
        //given
        given(repository.findById(ID)).willReturn(Optional.empty());

        //when
        service.getPostsById(ID);
    }

    @Test
    void should_get_posts_with_correctly_mapped_values_and_invoke_mandatory_services() {
        //given
        SocialNetworkPost post1 = buildPost(ID, AUTHOR_MICHAL);
        SocialNetworkPost post2 = buildPost(ID2, AUTHOR_MICHAL);
        PageRequest pageRequest = createPageRequest();
        PageCriteria pageCriteria = createPageCriteria();
        Page<SocialNetworkPost> page = new PageImpl<>(List.of(post2, post1));

        SocialNetworkPostDto expectedPostDto = buildPostDto(ID);
        SocialNetworkPostDto expectedPostDto2 = buildPostDto(ID2);
        List<SocialNetworkPostDto> expectedPostDtoList = List.of(expectedPostDto2, expectedPostDto);
        given(repository.findAll(pageRequest)).willReturn(page);
        given(mapper.map(refEq(post1), eq(SocialNetworkPostDto.class))).willReturn(expectedPostDto);
        given(mapper.map(refEq(post2), eq(SocialNetworkPostDto.class))).willReturn(expectedPostDto2);

        //when
        List<SocialNetworkPostDto> actualPostDtoList = service.getPosts(pageCriteria);

        //then
        assertThat(actualPostDtoList)
                .isNotNull()
                .hasSize(expectedPostDtoList.size())
                .hasSameElementsAs(expectedPostDtoList);
        verify(repository, Mockito.atMostOnce()).findAll(pageRequest);
        verify(mapper, Mockito.atMost(2)).map(any(), eq(SocialNetworkPostDto.class));

    }

    private static PageRequest createPageRequest() {
        return PageRequest.of(
                0,
                2,
                Sort.Direction.DESC,
                "viewCount"
        );
    }

    private static PageCriteria createPageCriteria() {
        return new PageCriteria(
                0,
                2,
                "viewCount",
                false
        );
    }

    @Test
    void should_create_post_and_get_proper_id_and_invoke_mandatory_services() {
        //given
        SocialNetworkPostInput socialNetworkPostInput = buildPostInput(AUTHOR_MICHAL);
        SocialNetworkPost post = buildPost(ID, AUTHOR_MICHAL);
        Long expectedIs = post.getId();
        given(repository.save(any())).willReturn(post);

        //when
        Long expectedId = service.createPost(socialNetworkPostInput);

        //then
        assertThat(expectedId).isEqualTo(expectedIs);
        verify(repository, Mockito.atMostOnce()).save(any());
    }

    private static SocialNetworkPostInput buildPostInput(String author) {
        return SocialNetworkPostInput.builder().author(author).content(TEST_CONTENT).build();
    }

    @Test
    void should_update_post_and_invoke_mandatory_services() {
        //given
        SocialNetworkPostDto changedPostDto = buildPostDto(ID);
        changedPostDto.setAuthor(AUTHOR_MAREK);

        SocialNetworkPost beforeChangesPost = buildPost(ID, AUTHOR_MICHAL);
        SocialNetworkPost updatedPost = buildPost(ID, AUTHOR_MAREK);
        given(repository.findById(ID)).willReturn(Optional.of(beforeChangesPost));
        given(mapper.map(refEq(changedPostDto), eq(SocialNetworkPost.class))).willReturn(updatedPost);
        given(repository.save(any())).willReturn(updatedPost);

        //when
        service.updatePost(ID, changedPostDto);

        //then
        verify(repository, Mockito.atMostOnce()).save(any());
        verify(repository, Mockito.atMostOnce()).findById(ID);
        verify(mapper, Mockito.atMostOnce()).map(refEq(changedPostDto), eq(SocialNetworkPost.class));
    }
    @Test(expectedExceptions = SocialNetworkPostNotFoundException.class)
    void should_throw_exception_when_not_found_post_during_update() {
        //given
        SocialNetworkPostDto changedPostDto = buildPostDto(ID);
        changedPostDto.setAuthor(AUTHOR_MAREK);
        given(repository.findById(ID)).willReturn(Optional.empty());

        //when
        service.updatePost(ID, changedPostDto);
    }

    @Test
    @DisplayName("should delete posts and invoke mandatory services")
    void should_delete_posts_and_invoke_mandatory_services() {
        //given
        SocialNetworkPost post = buildPost(ID, AUTHOR_MICHAL);
        given(repository.findById(ID)).willReturn(Optional.of(post));

        //when
        service.deletePost(ID);

        //then
        verify(repository, Mockito.atMostOnce()).deleteById(ID);
        verify(repository, Mockito.atMostOnce()).findById(ID);
    }

    @Test(expectedExceptions = SocialNetworkPostNotFoundException.class)
    @DisplayName("should throw exception when during deletion will not found post")
    void should_throw_exception_when_not_found_post_during_delete() {
        //given
        given(repository.findById(ID)).willReturn(Optional.empty());

        //when
        service.deletePost(ID);
    }

    private static SocialNetworkPost buildPost(Long id, String author) {
        return SocialNetworkPost
                .builder()
                .id(id)
                .postDate(LocalDate.now())
                .viewCount(0L)
                .author(author)
                .content(TEST_CONTENT)
                .version(1L)
                .build();
    }

    private static SocialNetworkPostDto buildPostDto(Long id) {
        return SocialNetworkPostDto
                .builder()
                .id(id)
                .postDate(LocalDate.now())
                .viewCount(0L)
                .author(AUTHOR_MICHAL)
                .content(TEST_CONTENT)
                .build();
    }

    @AfterMethod
    public void resetService() {
        service = null;
    }
}

