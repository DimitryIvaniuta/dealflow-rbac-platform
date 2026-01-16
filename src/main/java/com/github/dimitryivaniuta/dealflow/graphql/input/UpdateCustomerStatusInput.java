package com.github.dimitryivaniuta.dealflow.graphql.input;

import com.github.dimitryivaniuta.dealflow.domain.customer.CustomerStatus;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCustomerStatusInput {
    private UUID workspaceId;
    private UUID customerId;
    private CustomerStatus status;
}
