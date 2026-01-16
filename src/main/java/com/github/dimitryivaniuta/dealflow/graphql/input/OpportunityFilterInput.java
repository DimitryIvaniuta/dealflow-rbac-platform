package com.github.dimitryivaniuta.dealflow.graphql.input;

import com.github.dimitryivaniuta.dealflow.domain.pipeline.OpportunityStage;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpportunityFilterInput {
    private OpportunityStage stage;
    private UUID ownerMemberId;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
}
