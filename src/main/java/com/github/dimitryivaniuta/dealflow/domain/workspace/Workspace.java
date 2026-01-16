package com.github.dimitryivaniuta.dealflow.domain.workspace;

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
    name = "df_workspaces",
    indexes = {
        @Index(name = "ix_df_workspaces_slug", columnList = "slug", unique = true),
        @Index(name = "ix_df_workspaces_status", columnList = "status")
    }
)
public class Workspace extends AuditedEntity {

    @Column(name = "slug", nullable = false, length = 64, unique = true)
    private String slug;

    @Column(name = "display_name", nullable = false, length = 160)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private WorkspaceStatus status = WorkspaceStatus.ACTIVE;
}
