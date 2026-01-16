package com.github.dimitryivaniuta.dealflow.graphql.input;

import com.github.dimitryivaniuta.dealflow.domain.listing.ListingStatus;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListingFilterInput {
    private String city;
    private ListingStatus status;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
