package dev.evaldo.perfectnumber.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RangeRequest {
    @Schema(description = "Array contendo [inicio, fim]", example = "[1, 10000]")
    @NotNull
    private int[] range;
}
