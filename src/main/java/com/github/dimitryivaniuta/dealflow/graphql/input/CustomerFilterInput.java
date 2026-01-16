package com.github.dimitryivaniuta.dealflow.graphql.input;

import com.github.dimitryivaniuta.dealflow.domain.customer.CustomerStatus;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerFilterInput {
    private String text;
    private CustomerStatus status;
    private UUID ownerMemberId;
}
