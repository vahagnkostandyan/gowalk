package io.gowalk.gowalk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {
    @NotEmpty
    private List<String> interests;
}
