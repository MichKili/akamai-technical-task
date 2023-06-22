package com.akamai.technical.task.service;

import com.akamai.technical.task.exception.SocialNetworkPostNotFoundException;
import com.akamai.technical.task.model.SocialNetworkPost;
import com.akamai.technical.task.model.SocialNetworkPostInput;
import com.akamai.technical.task.model.dto.SocialNetworkPostDto;
import com.akamai.technical.task.repository.SocialNetworkPostRepository;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class) //why his dont work? When this exists there should not be needed any init/openMocks anymore..
public class SocialNetworkPostServiceImplTest {

    private static final long ID = 1L;
    private static final long ID2 = 2L;
    private static final String AUTHOR_MICHAL = "Michal";
    private static final String AUTHOR_MAREK = "Marek";
    private static final String TEST_CONTENT = "Test Post";

    private final SocialNetworkPostServiceTestHelper testHelper = new SocialNetworkPostServiceTestHelper();

    @Mock
    private SocialNetworkPostRepository repository;
    @Mock
    private Mapper mapper;
    @InjectMocks
    private SocialNetworkPostServiceImpl service;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
    }

    @Test
    void should_get_post_with_correctly_mapped_values_and_invoke_mandatory_services() {
        //given
        final var post = testHelper.buildPost(ID, AUTHOR_MICHAL, TEST_CONTENT);
        final var expectedPostDto = testHelper.buildPostDto(post);

        //when
        given(repository.findById(ID)).willReturn(Optional.of(post));
        given(mapper.map(refEq(post), eq(SocialNetworkPostDto.class))).willReturn(expectedPostDto);

        final var actualPostDto = service.getPostsById(ID);

        //then
        assertThat(actualPostDto).isEqualTo(expectedPostDto);
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
        final var post1 = testHelper.buildPost(ID, AUTHOR_MICHAL, TEST_CONTENT);
        final var post2 = testHelper.buildPost(ID2, AUTHOR_MICHAL, TEST_CONTENT);
        final var pageRequest = testHelper.createPageRequest();
        final var pageCriteria = testHelper.createPageCriteria();
        final var page = new PageImpl<>(List.of(post2, post1));

        final var expectedPostDto = testHelper.buildPostDto(post1);
        final var expectedPostDto2 = testHelper.buildPostDto(post2);
        final var expectedPostDtoList = List.of(expectedPostDto2, expectedPostDto);

        //when
        given(repository.findAll(pageRequest)).willReturn(page);
        given(mapper.map(refEq(post1), eq(SocialNetworkPostDto.class))).willReturn(expectedPostDto);
        given(mapper.map(refEq(post2), eq(SocialNetworkPostDto.class))).willReturn(expectedPostDto2);

        final var actualPostDtoList = service.getPosts(pageCriteria);

        //then
        assertThat(actualPostDtoList)
                .isNotNull()
                .hasSize(expectedPostDtoList.size())
                .hasSameElementsAs(expectedPostDtoList);
        verify(repository, Mockito.atMostOnce()).findAll(pageRequest);
        verify(mapper, Mockito.atMost(2)).map(any(), eq(SocialNetworkPostDto.class));

    }


    @Test
    void should_create_post_and_get_proper_id_and_invoke_mandatory_services() {
        //given
        final var socialNetworkPostInput = buildPostInput(AUTHOR_MICHAL);
        final var post = testHelper.buildPost(ID, AUTHOR_MICHAL, TEST_CONTENT);
        final var expectedIs = post.getId();

        //when
        given(repository.save(any())).willReturn(post);

        final var expectedId = service.createPost(socialNetworkPostInput);

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
        final var changedPostDto = testHelper.buildPostDto(ID, AUTHOR_MICHAL, TEST_CONTENT);
        changedPostDto.setAuthor(AUTHOR_MAREK);

        final var beforeChangesPost = testHelper.buildPost(ID, AUTHOR_MICHAL, TEST_CONTENT);
        final var updatedPost = testHelper.buildPost(ID, AUTHOR_MAREK, TEST_CONTENT);

        //when
        given(repository.findById(ID)).willReturn(Optional.of(beforeChangesPost));
        given(mapper.map(refEq(changedPostDto), eq(SocialNetworkPost.class))).willReturn(updatedPost);
        given(repository.save(any())).willReturn(updatedPost);

        service.updatePost(ID, changedPostDto);

        //then
        verify(repository, Mockito.atMostOnce()).save(any());
        verify(repository, Mockito.atMostOnce()).findById(ID);
        verify(mapper, Mockito.atMostOnce()).map(refEq(changedPostDto), eq(SocialNetworkPost.class));
    }

    @Test(expectedExceptions = SocialNetworkPostNotFoundException.class)
    void should_throw_exception_when_not_found_post_during_update() {
        //given
        final var changedPostDto = testHelper.buildPostDto(ID, AUTHOR_MICHAL, TEST_CONTENT);
        changedPostDto.setAuthor(AUTHOR_MAREK);

        //when
        given(repository.findById(ID)).willReturn(Optional.empty());

        service.updatePost(ID, changedPostDto);
    }

    @Test
    void should_delete_posts_and_invoke_mandatory_services() {
        //given
        final var post = testHelper.buildPost(ID, AUTHOR_MICHAL, TEST_CONTENT);

        //when
        given(repository.findById(ID)).willReturn(Optional.of(post));

        service.deletePost(ID);

        //then
        verify(repository, Mockito.atMostOnce()).deleteById(ID);
        verify(repository, Mockito.atMostOnce()).findById(ID);
    }

    @Test(expectedExceptions = SocialNetworkPostNotFoundException.class)
    void should_throw_exception_when_not_found_post_during_delete() {
        //when
        given(repository.findById(ID)).willReturn(Optional.empty());

        service.deletePost(ID);
    }


    @AfterMethod
    public void resetService() {
        service = null;
    }
}

