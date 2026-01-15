package com.github.dimitryivaniuta.dealflow.service.customer;

import com.github.dimitryivaniuta.dealflow.domain.customer.Customer;
import com.github.dimitryivaniuta.dealflow.domain.customer.CustomerStatus;
import com.github.dimitryivaniuta.dealflow.domain.workspace.Workspace;
import com.github.dimitryivaniuta.dealflow.domain.workspace.WorkspaceMember;
import com.github.dimitryivaniuta.dealflow.graphql.input.CreateCustomerInput;
import com.github.dimitryivaniuta.dealflow.graphql.input.CustomerFilterInput;
import com.github.dimitryivaniuta.dealflow.repo.customer.CustomerRepository;
import com.github.dimitryivaniuta.dealflow.repo.workspace.WorkspaceMemberRepository;
import com.github.dimitryivaniuta.dealflow.repo.workspace.WorkspaceRepository;
import com.github.dimitryivaniuta.dealflow.service.TextNormalizer;
import com.github.dimitryivaniuta.dealflow.service.exception.BadRequestException;
import com.github.dimitryivaniuta.dealflow.service.exception.NotFoundException;
import com.github.dimitryivaniuta.dealflow.service.spec.CustomerSpecifications;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Customer> search(UUID workspaceId, CustomerFilterInput filter, Pageable pageable) {
        Specification<Customer> spec = CustomerSpecifications.byFilter(workspaceId, filter);
        return customerRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public Customer create(CreateCustomerInput input) {
        if (input == null) {
            throw new BadRequestException("input is required");
        }
        Workspace workspace = workspaceRepository.findById(input.getWorkspaceId())
            .orElseThrow(() -> new NotFoundException("workspace not found"));

        Customer customer = new Customer();
        customer.setWorkspace(workspace);
        customer.setDisplayName(requireNonBlank(input.getDisplayName(), "displayName"));
        customer.setNormalizedName(TextNormalizer.normalize(customer.getDisplayName()));
        customer.setEmail(input.getEmail());
        customer.setPhone(input.getPhone());
        customer.setExternalRef(input.getExternalRef());

        if (input.getOwnerMemberId() != null) {
            WorkspaceMember owner = memberRepository.findById(input.getOwnerMemberId())
                .orElseThrow(() -> new NotFoundException("owner member not found"));
            customer.setOwner(owner);
        }

        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer updateStatus(UUID workspaceId, UUID customerId, CustomerStatus status) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new NotFoundException("customer not found"));
        if (!customer.getWorkspace().getId().equals(workspaceId)) {
            throw new NotFoundException("customer not found");
        }
        customer.setStatus(status);
        return customerRepository.save(customer);
    }

    private static String requireNonBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new BadRequestException(field + " is required");
        }
        return value.trim();
    }
}
