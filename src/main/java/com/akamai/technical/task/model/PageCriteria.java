package com.akamai.technical.task.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PageCriteria(@NotNull @PositiveOrZero Integer pageNumber,
                           @NotNull @PositiveOrZero Integer pageSize,
                           @NotNull @NotBlank @NotEmpty String sortProperty,
                           @NotNull Boolean ascending) {
}
