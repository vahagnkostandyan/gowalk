package io.gowalk.gowalk.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
public class GetStoryRequest {
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    private Mode mode;
}
