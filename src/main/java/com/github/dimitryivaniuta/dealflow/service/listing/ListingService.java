package com.github.dimitryivaniuta.dealflow.service.listing;

import com.github.dimitryivaniuta.dealflow.domain.listing.Listing;
import com.github.dimitryivaniuta.dealflow.graphql.input.CreateListingInput;
import com.github.dimitryivaniuta.dealflow.graphql.input.ListingFilterInput;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListingService {
    Page<Listing> search(UUID workspaceId, ListingFilterInput filter, Pageable pageable);
    Listing create(CreateListingInput input);
    Listing publish(UUID workspaceId, UUID listingId);
}
