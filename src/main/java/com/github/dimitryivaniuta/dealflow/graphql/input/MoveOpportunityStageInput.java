package com.github.dimitryivaniuta.dealflow.graphql.input;

import com.github.dimitryivaniuta.dealflow.domain.pipeline.OpportunityStage;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveOpportunityStageInput {
    private UUID workspaceId;
    private UUID opportunityId;
    private OpportunityStage stage;
}
