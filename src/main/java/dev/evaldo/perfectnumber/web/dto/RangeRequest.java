package dev.evaldo.perfectnumber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RangeRequest {
    @Schema(description = "Array contendo [inicio, fim]", example = "[1, 10000]")
    @NotNull
    private Integer[] range;

}
