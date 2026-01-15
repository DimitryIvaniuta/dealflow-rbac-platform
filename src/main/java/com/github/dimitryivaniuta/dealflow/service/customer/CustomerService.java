package com.github.dimitryivaniuta.dealflow.service.customer;

import com.github.dimitryivaniuta.dealflow.domain.customer.Customer;
import com.github.dimitryivaniuta.dealflow.domain.customer.CustomerStatus;
import com.github.dimitryivaniuta.dealflow.graphql.input.CreateCustomerInput;
import com.github.dimitryivaniuta.dealflow.graphql.input.CustomerFilterInput;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Page<Customer> search(UUID workspaceId, CustomerFilterInput filter, Pageable pageable);
    Customer create(CreateCustomerInput input);
    Customer updateStatus(UUID workspaceId, UUID customerId, CustomerStatus status);
}
