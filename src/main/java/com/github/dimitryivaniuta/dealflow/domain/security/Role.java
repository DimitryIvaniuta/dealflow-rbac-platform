package com.github.dimitryivaniuta.dealflow.domain.security;

import com.github.dimitryivaniuta.dealflow.domain.common.AuditedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "df_roles",
    indexes = {
        @Index(name = "ix_df_roles_key", columnList = "role_key", unique = true)
    }
)
public class Role extends AuditedEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "role_key", nullable = false, length = 32, unique = true)
    private RoleKey roleKey;

    @Column(name = "display_name", nullable = false, length = 120)
    private String displayName;

    @Column(name = "is_system", nullable = false)
    private boolean system = true;
}
