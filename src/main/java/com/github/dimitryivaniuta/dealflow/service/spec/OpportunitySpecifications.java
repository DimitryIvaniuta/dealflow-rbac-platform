package com.github.dimitryivaniuta.dealflow.service.spec;

import com.github.dimitryivaniuta.dealflow.domain.pipeline.Opportunity;
import com.github.dimitryivaniuta.dealflow.domain.pipeline.OpportunityStage;
import com.github.dimitryivaniuta.dealflow.graphql.input.OpportunityFilterInput;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public final class OpportunitySpecifications {

    private OpportunitySpecifications() {}

    public static Specification<Opportunity> forWorkspace(UUID workspaceId) {
        return (root, query, cb) -> cb.equal(root.get("workspace").get("id"), workspaceId);
    }

    public static Specification<Opportunity> byFilter(UUID workspaceId, OpportunityFilterInput filter) {
        Specification<Opportunity> spec = forWorkspace(workspaceId);

        if (filter == null) {
            return spec;
        }

        OpportunityStage stage = filter.getStage();
        if (stage != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("stage"), stage));
        }

        UUID ownerMemberId = filter.getOwnerMemberId();
        if (ownerMemberId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("owner").get("id"), ownerMemberId));
        }

        BigDecimal min = filter.getMinAmount();
        if (min != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("amount"), min));
        }

        BigDecimal max = filter.getMaxAmount();
        if (max != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("amount"), max));
        }

        return spec;
    }
}
