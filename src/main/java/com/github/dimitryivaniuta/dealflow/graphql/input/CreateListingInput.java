package com.github.dimitryivaniuta.dealflow.graphql.input;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateListingInput {
    private UUID workspaceId;
    private String title;
    private String city;
    private BigDecimal askingPrice;
    private UUID customerId;
}
