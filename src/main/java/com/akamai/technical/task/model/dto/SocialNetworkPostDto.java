package com.akamai.technical.task.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SocialNetworkPostDto {
    @PositiveOrZero
    @NotNull
    private Long id;
    @Size(min = 1, max = 256)
    @NotNull(message = "Content can't be null")
    @NotBlank
    private String content;
    @Size(min = 1, max = 256)
    @NotNull
    @NotBlank
    private String author;
    @PositiveOrZero
    @NotNull
    private Long viewCount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate postDate;
}
