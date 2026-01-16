package com.github.dimitryivaniuta.dealflow.graphql.input;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublishListingInput {
    private UUID workspaceId;
    private UUID listingId;
}
