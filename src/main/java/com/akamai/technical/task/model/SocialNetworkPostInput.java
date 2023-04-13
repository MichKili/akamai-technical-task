package com.akamai.technical.task.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SocialNetworkPostInput(
        @Size(min = 1, max = 256) @NotNull @NotBlank String content,
        @Size(min = 1, max = 256) @NotNull @NotBlank String author
) {
}
