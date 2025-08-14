package dev.evaldo.perfectnumber.infrastructure.services;

import dev.evaldo.perfectnumber.domain.PerfectNumberService;
import dev.evaldo.perfectnumber.infrastructure.persistence.AuditLogEntity;
import dev.evaldo.perfectnumber.infrastructure.persistence.AuditLogRepository;
import dev.evaldo.perfectnumber.web.dto.PerfectNumbersResponse;
import dev.evaldo.perfectnumber.web.dto.RangeRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerfectNumberServiceImpl implements PerfectNumberService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public Optional<PerfectNumbersResponse> processRange(RangeRequest request, HttpServletRequest http) {
        Integer[] r = request.getRange();
        if (r == null || r.length != 2) {
            return Optional.empty();
        }
        int start = r[0];
        int end = r[1];
        List<Integer> perfects = findPerfectInRange(start, end);
        List<Integer> primes = primesOf(perfects);
        String primesCsv = primes.stream().map(String::valueOf).collect(Collectors.joining(","));
        auditLogRepository.save(
                AuditLogEntity.builder()
                    .sourceIp(getClientIp(http))
                    .requestPath(http.getRequestURI())
                    .rangeStart(start)
                    .rangeEnd(end)
                    .primesCsv(primesCsv)
                    .build());
        return Optional.of(new PerfectNumbersResponse(perfects, perfects.size()));
    }

    @Override
    public boolean isPerfect(int n) {
        if (n < 2) return false;
        int sum = 1;
        int sqrt = (int)Math.sqrt(n);
        for (int i = 2; i <= sqrt; i++) {
            if (n % i == 0) {
                sum += i;
                int d = n / i;
                if (d != i) sum += d;
            }
        }
        return sum == n;
    }

    @Override
    public List<Integer> primesOf(List<Integer> numbers) {
        List<Integer> primes = new ArrayList<>();
        for (Integer n : numbers) {
            if (n != null && isPrime(n)) primes.add(n);
        }
        return primes;
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        if (n % 2 == 0) return n == 2;
        int sqrt = (int)Math.sqrt(n);
        for (int i = 3; i <= sqrt; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private List<Integer> findPerfectInRange(int start, int end) {
        List<Integer> perfects = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isPerfect(i)) {
                perfects.add(i);
            }
        }
        return perfects;
    }

    private String getClientIp(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        if (xf != null && !xf.isBlank()) {
            return xf.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

}
