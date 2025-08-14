package dev.evaldo.perfectnumber.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long> { }
