package dev.evaldo.perfectnumber.web;

import dev.evaldo.perfectnumber.domain.PerfectNumberService;
import dev.evaldo.perfectnumber.web.dto.PerfectNumbersResponse;
import dev.evaldo.perfectnumber.web.dto.RangeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/perfect-numbers")
@RequiredArgsConstructor
@Tag(name = "Perfect Numbers")
public class PerfectNumberController {

    private final PerfectNumberService perfectNumberService;

    @PostMapping("/find")
    @Operation(summary = "Find perfect numbers in a range",
               description = "Recebe um array [start, end] e retorna todos os números perfeitos no intervalo. " +
                       "Também registra um audit log contendo o IP de origem e os números primos do resultado.")
    public ResponseEntity<PerfectNumbersResponse> find(@Valid @RequestBody RangeRequest request,
                                                       HttpServletRequest http) {
        return perfectNumberService.processRange(request, http)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
