package com.github.dimitryivaniuta.dealflow.service.pipeline;

import com.github.dimitryivaniuta.dealflow.domain.pipeline.Opportunity;
import com.github.dimitryivaniuta.dealflow.domain.pipeline.OpportunityStage;
import com.github.dimitryivaniuta.dealflow.graphql.input.CreateOpportunityInput;
import com.github.dimitryivaniuta.dealflow.graphql.input.OpportunityFilterInput;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OpportunityService {
    Page<Opportunity> search(UUID workspaceId, OpportunityFilterInput filter, Pageable pageable);
    Opportunity create(CreateOpportunityInput input);
    Opportunity moveStage(UUID workspaceId, UUID opportunityId, OpportunityStage stage);
}
