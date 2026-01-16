package com.github.dimitryivaniuta.dealflow.domain.security;

import com.github.dimitryivaniuta.dealflow.domain.common.AuditedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "df_role_permissions",
    uniqueConstraints = @UniqueConstraint(name = "uq_df_role_permissions_role_perm", columnNames = {"role_id", "permission_id"}),
    indexes = {
        @Index(name = "ix_df_role_permissions_role", columnList = "role_id"),
        @Index(name = "ix_df_role_permissions_perm", columnList = "permission_id")
    }
)
public class RolePermission extends AuditedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;
}
