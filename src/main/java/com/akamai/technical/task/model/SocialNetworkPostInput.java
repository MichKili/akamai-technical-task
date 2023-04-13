package com.akamai.technical.task.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SocialNetworkPostInput(
        @Size(min = 1, max = 256) @NotNull @NotBlank String content,
        @Size(min = 1, max = 256) @NotNull @NotBlank String author
) {
}
