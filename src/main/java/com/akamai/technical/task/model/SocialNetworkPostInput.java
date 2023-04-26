package com.akamai.technical.task.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SocialNetworkPostInput(
        @Size(min = 1, max = 256, message = "Size has to be between 1 and 256 chars")
        @NotBlank(message = "This cannot be blank value")
        String content,
        @Size(min = 1, max = 256, message = "Size has to be between 1 and 256 chars")
        @NotBlank(message = "This cannot be blank value")
        String author
) {
}
