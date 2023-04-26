package com.akamai.technical.task.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PageCriteria(@PositiveOrZero(message = "Value should be 0 or positive")
                           @NotNull(message = "This cannot be null value") //MUST BE because u can add null to PositiveZero null elements are considered valid.
                           Integer pageNumber,
                           @PositiveOrZero(message = "Value should be 0 or positive")
                           @NotNull(message = "This cannot be null value")
                           Integer pageSize,
                           @NotBlank(message = "This cannot be blank value")
                           String sortProperty, //not nul and not empty are included in this annotation
                           @NotNull(message = "This cannot be null value")
                           Boolean ascending) {
}
