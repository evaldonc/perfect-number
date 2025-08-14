package dev.evaldo.perfectnumber.infrastructure.mappers;

import dev.evaldo.perfectnumber.infrastructure.persistence.AuditLogEntity;
import dev.evaldo.perfectnumber.domain.AuditLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    AuditLog toDomain(AuditLogEntity entity);

    AuditLogEntity toEntity(AuditLog domain);

}
