package dev.evaldo.perfectnumber.domain;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class AuditLog {
    Long id;
    Instant createdAt;
    String sourceIp;
    String requestPath;
    Integer rangeStart;
    Integer rangeEnd;
    String primesCsv;
}
