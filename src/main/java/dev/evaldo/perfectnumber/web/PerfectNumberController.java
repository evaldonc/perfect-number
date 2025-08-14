package dev.evaldo.perfectnumber.web;

import dev.evaldo.perfectnumber.domain.PerfectNumberService;
import dev.evaldo.perfectnumber.infrastructure.persistence.AuditLogEntity;
import dev.evaldo.perfectnumber.infrastructure.persistence.AuditLogRepository;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/perfect-numbers")
@RequiredArgsConstructor
@Tag(name = "Perfect Numbers")
public class PerfectNumberController {

    private final PerfectNumberService perfectNumberService;
    private final AuditLogRepository auditLogRepository;

    @PostMapping("/find")
    @Operation(summary = "Find perfect numbers in a range",
               description = "Recebe um array [start, end] e retorna todos os números perfeitos no intervalo. " +
                       "Também registra um audit log contendo o IP de origem e os números primos do resultado.")
    public ResponseEntity<PerfectNumbersResponse> find(@Valid @RequestBody RangeRequest request,
                                                       HttpServletRequest http) {
        int[] r = request.getRange();
        if (r == null || r.length != 2) {
            return ResponseEntity.badRequest().build();
        }
        int start = r[0];
        int end = r[1];
        List<Integer> perfects = perfectNumberService.findPerfectInRange(start, end);

        // Compute primes from the results (maybe empty because perfect numbers > 1 are not prime).
        List<Integer> primes = perfectNumberService.primesOf(perfects);
        String primesCsv = primes.stream().map(String::valueOf).collect(Collectors.joining(","));

        AuditLogEntity log = new AuditLogEntity();
        log.setSourceIp(getClientIp(http));
        log.setRequestPath(http.getRequestURI());
        log.setRangeStart(start);
        log.setRangeEnd(end);
        log.setPrimesCsv(primesCsv);
        auditLogRepository.save(log);

        return ResponseEntity.ok(new PerfectNumbersResponse(perfects, perfects.size()));
    }

    private String getClientIp(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        if (xf != null && !xf.isBlank()) {
            return xf.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
