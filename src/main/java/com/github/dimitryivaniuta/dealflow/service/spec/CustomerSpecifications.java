package com.github.dimitryivaniuta.dealflow.service.spec;

import com.github.dimitryivaniuta.dealflow.domain.customer.Customer;
import com.github.dimitryivaniuta.dealflow.domain.customer.CustomerStatus;
import com.github.dimitryivaniuta.dealflow.graphql.input.CustomerFilterInput;
import java.util.Locale;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public final class CustomerSpecifications {

    private CustomerSpecifications() {}

    public static Specification<Customer> forWorkspace(UUID workspaceId) {
        return (root, query, cb) -> cb.equal(root.get("workspace").get("id"), workspaceId);
    }

    public static Specification<Customer> byFilter(UUID workspaceId, CustomerFilterInput filter) {
        Specification<Customer> spec = forWorkspace(workspaceId);

        if (filter == null) {
            return spec;
        }

        if (filter.getText() != null && !filter.getText().isBlank()) {
            String like = "%" + filter.getText().trim().toLowerCase(Locale.ROOT) + "%";
            spec = spec.and((root, query, cb) -> cb.like(root.get("normalizedName"), like));
        }

        CustomerStatus status = filter.getStatus();
        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        UUID ownerMemberId = filter.getOwnerMemberId();
        if (ownerMemberId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("owner").get("id"), ownerMemberId));
        }

        return spec;
    }
}
