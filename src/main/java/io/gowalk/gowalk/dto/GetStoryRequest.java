package io.gowalk.gowalk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.gowalk.gowalk.enumerations.Mode;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetStoryRequest {
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    private Mode mode;
}
