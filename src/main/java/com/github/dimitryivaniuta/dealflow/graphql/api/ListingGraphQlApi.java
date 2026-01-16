package com.github.dimitryivaniuta.dealflow.graphql.api;

import com.github.dimitryivaniuta.dealflow.domain.listing.Listing;
import com.github.dimitryivaniuta.dealflow.domain.security.PermissionCode;
import com.github.dimitryivaniuta.dealflow.graphql.input.CreateListingInput;
import com.github.dimitryivaniuta.dealflow.graphql.input.ListingFilterInput;
import com.github.dimitryivaniuta.dealflow.graphql.input.PublishListingInput;
import com.github.dimitryivaniuta.dealflow.service.listing.ListingService;
import io.leangen.graphql.annotations.GraphQLApi;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@GraphQLApi
@Component
@RequiredArgsConstructor
public class ListingGraphQlApi {

    private final ListingService listingService;

    @GraphQLQuery(name = "listings")
    @PreAuthorize("@wsSec.hasPermission(#workspaceId, T(com.github.dimitryivaniuta.dealflow.domain.security.PermissionCode).LISTING_READ)")
    public Page<Listing> listings(
        @GraphQLArgument(name = "workspaceId") UUID workspaceId,
        @GraphQLArgument(name = "filter") ListingFilterInput filter,
        @GraphQLArgument(name = "page") int page,
        @GraphQLArgument(name = "size") int size
    ) {
        return listingService.search(workspaceId, filter, PageRequest.of(Math.max(0, page), Math.min(Math.max(1, size), 200)));
    }

    @GraphQLMutation(name = "createListing")
    @PreAuthorize("@wsSec.hasPermission(#input.workspaceId, T(com.github.dimitryivaniuta.dealflow.domain.security.PermissionCode).LISTING_WRITE)")
    public Listing createListing(@GraphQLArgument(name = "input") CreateListingInput input) {
        return listingService.create(input);
    }

    @GraphQLMutation(name = "publishListing")
    @PreAuthorize("@wsSec.hasPermission(#input.workspaceId, T(com.github.dimitryivaniuta.dealflow.domain.security.PermissionCode).LISTING_WRITE)")
    public Listing publishListing(@GraphQLArgument(name = "input") PublishListingInput input) {
        return listingService.publish(input.getWorkspaceId(), input.getListingId());
    }
}
