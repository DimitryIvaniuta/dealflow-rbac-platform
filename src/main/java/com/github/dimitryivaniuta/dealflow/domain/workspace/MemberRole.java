package com.github.dimitryivaniuta.dealflow.domain.workspace;

import com.github.dimitryivaniuta.dealflow.domain.common.AuditedEntity;
import com.github.dimitryivaniuta.dealflow.domain.security.Role;
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
    name = "df_member_roles",
    uniqueConstraints = @UniqueConstraint(name = "uq_df_member_roles_member_role", columnNames = {"member_id", "role_id"}),
    indexes = {
        @Index(name = "ix_df_member_roles_member", columnList = "member_id"),
        @Index(name = "ix_df_member_roles_role", columnList = "role_id")
    }
)
public class MemberRole extends AuditedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private WorkspaceMember member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
