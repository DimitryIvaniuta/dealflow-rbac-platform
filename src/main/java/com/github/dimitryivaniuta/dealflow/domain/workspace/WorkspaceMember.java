package com.github.dimitryivaniuta.dealflow.domain.workspace;

import com.github.dimitryivaniuta.dealflow.domain.common.AuditedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    name = "df_workspace_members",
    uniqueConstraints = @UniqueConstraint(name = "uq_df_workspace_members_ws_subject", columnNames = {"workspace_id", "subject"}),
    indexes = {
        @Index(name = "ix_df_workspace_members_ws", columnList = "workspace_id"),
        @Index(name = "ix_df_workspace_members_subject", columnList = "subject"),
        @Index(name = "ix_df_workspace_members_status", columnList = "status")
    }
)
public class WorkspaceMember extends AuditedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    /**
     * Stable identity from JWT (sub / preferred_username / userId). We use it for RBAC lookup.
     */
    @Column(name = "subject", nullable = false, length = 128)
    private String subject;

    @Column(name = "email", nullable = false, length = 180)
    private String email;

    @Column(name = "display_name", nullable = false, length = 160)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private MemberStatus status = MemberStatus.ACTIVE;
}
