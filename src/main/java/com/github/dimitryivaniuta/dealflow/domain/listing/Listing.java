package com.github.dimitryivaniuta.dealflow.domain.listing;

import com.github.dimitryivaniuta.dealflow.domain.common.AuditedEntity;
import com.github.dimitryivaniuta.dealflow.domain.customer.Customer;
import com.github.dimitryivaniuta.dealflow.domain.workspace.Workspace;
import java.math.BigDecimal;
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
    name = "df_listings",
    indexes = {
        @Index(name = "ix_df_listings_ws", columnList = "workspace_id"),
        @Index(name = "ix_df_listings_status", columnList = "status"),
        @Index(name = "ix_df_listings_city", columnList = "city_normalized")
    }
)
public class Listing extends AuditedEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @Column(name = "title", nullable = false, length = 220)
    private String title;

    @Column(name = "city", nullable = false, length = 120)
    private String city;

    @Column(name = "city_normalized", nullable = false, length = 120)
    private String cityNormalized;

    @Column(name = "asking_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal askingPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private ListingStatus status = ListingStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
