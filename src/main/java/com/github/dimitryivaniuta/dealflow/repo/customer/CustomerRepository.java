package com.github.dimitryivaniuta.dealflow.repo.customer;

import com.github.dimitryivaniuta.dealflow.domain.customer.Customer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {
}
