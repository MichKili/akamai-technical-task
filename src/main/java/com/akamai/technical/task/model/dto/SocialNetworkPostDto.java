package com.akamai.technical.task.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SocialNetworkPostDto {
    @PositiveOrZero(message = "Value should be 0 or positive")
    @NotNull(message = "This cannot be null value")
    //MUST BE because u can add null to PositiveZero null elements are considered valid.
    private Long id;

    @Size(min = 1, max = 256, message = "Size has to be between 1 and 256 char")
    @NotBlank(message = "This cannot be blank value")
    private String content;
    @Size(min = 1, max = 256, message = "Size has to be between 1 and 256 char")
    @NotBlank(message = "This cannot be blank value")
    private String author;
    @PositiveOrZero(message = "Value should be 0 or positive")
    @NotNull(message = "This cannot be null value")
    private Long viewCount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "This cannot be null value")
    private LocalDate postDate;

    private Long version;

    private Set<Long> idList;
}
