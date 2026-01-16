package com.github.dimitryivaniuta.dealflow.graphql.input;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOpportunityInput {
    private UUID workspaceId;
    private String title;
    private BigDecimal amount;
    private LocalDate expectedCloseDate;
    private UUID customerId;
    private UUID ownerMemberId;
}
