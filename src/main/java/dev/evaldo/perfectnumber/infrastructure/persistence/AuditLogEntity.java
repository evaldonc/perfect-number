package dev.evaldo.perfectnumber.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false, length = 64)
    private String sourceIp;

    @Column(nullable = false, length = 255)
    private String requestPath;

    private Integer rangeStart;

    private Integer rangeEnd;

    @Column(columnDefinition = "text")
    private String primesCsv;
}
