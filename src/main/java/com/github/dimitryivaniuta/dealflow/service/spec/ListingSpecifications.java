package com.github.dimitryivaniuta.dealflow.service.spec;

import com.github.dimitryivaniuta.dealflow.domain.listing.Listing;
import com.github.dimitryivaniuta.dealflow.domain.listing.ListingStatus;
import com.github.dimitryivaniuta.dealflow.graphql.input.ListingFilterInput;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public final class ListingSpecifications {

    private ListingSpecifications() {}

    public static Specification<Listing> forWorkspace(UUID workspaceId) {
        return (root, query, cb) -> cb.equal(root.get("workspace").get("id"), workspaceId);
    }

    public static Specification<Listing> byFilter(UUID workspaceId, ListingFilterInput filter) {
        Specification<Listing> spec = forWorkspace(workspaceId);

        if (filter == null) {
            return spec;
        }

        if (filter.getCity() != null && !filter.getCity().isBlank()) {
            String city = filter.getCity().trim().toLowerCase(Locale.ROOT);
            spec = spec.and((root, query, cb) -> cb.equal(root.get("cityNormalized"), city));
        }

        ListingStatus status = filter.getStatus();
        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        BigDecimal min = filter.getMinPrice();
        if (min != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("askingPrice"), min));
        }

        BigDecimal max = filter.getMaxPrice();
        if (max != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("askingPrice"), max));
        }

        return spec;
    }
}
