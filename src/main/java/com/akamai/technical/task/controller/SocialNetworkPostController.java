package com.akamai.technical.task.controller;

import com.akamai.technical.task.model.PageCriteria;
import com.akamai.technical.task.model.SocialNetworkPostInput;
import com.akamai.technical.task.model.dto.SocialNetworkPostDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;
import java.util.List;


@RequestMapping("api/v1/posts")
@Validated
public interface SocialNetworkPostController {

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    SocialNetworkPostDto getPostById(@PathVariable @NonNull Long id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<SocialNetworkPostDto> getPosts(@Valid PageCriteria pageRequest);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Long createPost(@RequestBody @Valid SocialNetworkPostInput input);

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    void updatePost(@PathVariable @NotNull @PositiveOrZero Long id,
                    @RequestParam Collection<Long> idList,
                    @RequestBody @Valid SocialNetworkPostDto input);

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePost(@PathVariable @NotNull @PositiveOrZero Long id);
}
