package com.convertino.hermesspringapi.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Range;


@Data
public class FeedbackRequestDTO {
    @NotNull(message = "text cannot be null")
    @NotEmpty(message = "text cannot be empty")
    private String text;

    @NotNull(message = "label cannot be null")
    @Range(min = 0, max = 1, message = "label must 0 or 1")
    private Integer label;
}
