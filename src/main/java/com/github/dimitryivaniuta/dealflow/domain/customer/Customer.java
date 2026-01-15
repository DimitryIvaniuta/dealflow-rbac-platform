package com.github.dimitryivaniuta.dealflow.domain.customer;

import com.github.dimitryivaniuta.dealflow.domain.common.AuditedEntity;
import com.github.dimitryivaniuta.dealflow.domain.workspace.Workspace;
import com.github.dimitryivaniuta.dealflow.domain.workspace.WorkspaceMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "df_customers",
    indexes = {
        @Index(name = "ix_df_customers_ws", columnList = "workspace_id"),
        @Index(name = "ix_df_customers_status", columnList = "status"),
        @Index(name = "ix_df_customers_name", columnList = "normalized_name")
    }
)
public class Customer extends AuditedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @Column(name = "display_name", nullable = false, length = 200)
    private String displayName;

    @Column(name = "normalized_name", nullable = false, length = 200)
    private String normalizedName;

    @Column(name = "email", nullable = true, length = 180)
    private String email;

    @Column(name = "phone", nullable = true, length = 40)
    private String phone;

    @Column(name = "external_ref", nullable = true, length = 80)
    private String externalRef;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private CustomerStatus status = CustomerStatus.NEW;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_member_id")
    private WorkspaceMember owner;
}
