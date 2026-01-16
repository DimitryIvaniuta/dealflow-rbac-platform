package com.github.dimitryivaniuta.dealflow.graphql.api;

import com.github.dimitryivaniuta.dealflow.domain.customer.Customer;
import com.github.dimitryivaniuta.dealflow.domain.customer.CustomerStatus;
import com.github.dimitryivaniuta.dealflow.domain.security.PermissionCode;
import com.github.dimitryivaniuta.dealflow.graphql.input.CreateCustomerInput;
import com.github.dimitryivaniuta.dealflow.graphql.input.CustomerFilterInput;
import com.github.dimitryivaniuta.dealflow.graphql.input.UpdateCustomerStatusInput;
import com.github.dimitryivaniuta.dealflow.service.customer.CustomerService;
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
public class CustomerGraphQlApi {

    private final CustomerService customerService;

    @GraphQLQuery(name = "customers")
    @PreAuthorize("@wsSec.hasPermission(#workspaceId, T(com.github.dimitryivaniuta.dealflow.domain.security.PermissionCode).CUSTOMER_READ)")
    public Page<Customer> customers(
        @GraphQLArgument(name = "workspaceId") UUID workspaceId,
        @GraphQLArgument(name = "filter") CustomerFilterInput filter,
        @GraphQLArgument(name = "page") int page,
        @GraphQLArgument(name = "size") int size
    ) {
        return customerService.search(workspaceId, filter, PageRequest.of(Math.max(0, page), Math.min(Math.max(1, size), 200)));
    }

    @GraphQLMutation(name = "createCustomer")
    @PreAuthorize("@wsSec.hasPermission(#input.workspaceId, T(com.github.dimitryivaniuta.dealflow.domain.security.PermissionCode).CUSTOMER_WRITE)")
    public Customer createCustomer(@GraphQLArgument(name = "input") CreateCustomerInput input) {
        return customerService.create(input);
    }

    @GraphQLMutation(name = "updateCustomerStatus")
    @PreAuthorize("@wsSec.hasPermission(#input.workspaceId, T(com.github.dimitryivaniuta.dealflow.domain.security.PermissionCode).CUSTOMER_WRITE)")
    public Customer updateCustomerStatus(@GraphQLArgument(name = "input") UpdateCustomerStatusInput input) {
        return customerService.updateStatus(input.getWorkspaceId(), input.getCustomerId(), input.getStatus());
    }
}
