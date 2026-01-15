package com.github.dimitryivaniuta.dealflow.domain.security;

import com.github.dimitryivaniuta.dealflow.domain.common.AuditedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "df_permissions",
    indexes = {
        @Index(name = "ix_df_permissions_code", columnList = "code", unique = true)
    }
)
public class Permission extends AuditedEntity {

    @Column(name = "code", nullable = false, length = 64, unique = true)
    private PermissionCode code;

    @Column(name = "description", nullable = false, length = 200)
    private String description;
}
