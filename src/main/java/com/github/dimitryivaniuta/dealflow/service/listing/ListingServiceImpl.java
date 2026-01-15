package com.github.dimitryivaniuta.dealflow.service.listing;

import com.github.dimitryivaniuta.dealflow.domain.customer.Customer;
import com.github.dimitryivaniuta.dealflow.domain.listing.Listing;
import com.github.dimitryivaniuta.dealflow.domain.listing.ListingStatus;
import com.github.dimitryivaniuta.dealflow.domain.workspace.Workspace;
import com.github.dimitryivaniuta.dealflow.graphql.input.CreateListingInput;
import com.github.dimitryivaniuta.dealflow.graphql.input.ListingFilterInput;
import com.github.dimitryivaniuta.dealflow.repo.customer.CustomerRepository;
import com.github.dimitryivaniuta.dealflow.repo.listing.ListingRepository;
import com.github.dimitryivaniuta.dealflow.repo.workspace.WorkspaceRepository;
import com.github.dimitryivaniuta.dealflow.service.TextNormalizer;
import com.github.dimitryivaniuta.dealflow.service.exception.BadRequestException;
import com.github.dimitryivaniuta.dealflow.service.exception.NotFoundException;
import com.github.dimitryivaniuta.dealflow.service.spec.ListingSpecifications;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final WorkspaceRepository workspaceRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Listing> search(UUID workspaceId, ListingFilterInput filter, Pageable pageable) {
        Specification<Listing> spec = ListingSpecifications.byFilter(workspaceId, filter);
        return listingRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public Listing create(CreateListingInput input) {
        if (input == null) {
            throw new BadRequestException("input is required");
        }
        Workspace workspace = workspaceRepository.findById(input.getWorkspaceId())
            .orElseThrow(() -> new NotFoundException("workspace not found"));

        Listing listing = new Listing();
        listing.setWorkspace(workspace);
        listing.setTitle(requireNonBlank(input.getTitle(), "title"));
        listing.setCity(requireNonBlank(input.getCity(), "city"));
        listing.setCityNormalized(TextNormalizer.normalize(listing.getCity()));
        listing.setAskingPrice(input.getAskingPrice());

        if (input.getCustomerId() != null) {
            Customer customer = customerRepository.findById(input.getCustomerId())
                .orElseThrow(() -> new NotFoundException("customer not found"));
            listing.setCustomer(customer);
        }

        return listingRepository.save(listing);
    }

    @Override
    @Transactional
    public Listing publish(UUID workspaceId, UUID listingId) {
        Listing listing = listingRepository.findById(listingId)
            .orElseThrow(() -> new NotFoundException("listing not found"));
        if (!listing.getWorkspace().getId().equals(workspaceId)) {
            throw new NotFoundException("listing not found");
        }
        listing.setStatus(ListingStatus.PUBLISHED);
        return listingRepository.save(listing);
    }

    private static String requireNonBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new BadRequestException(field + " is required");
        }
        return value.trim();
    }
}
