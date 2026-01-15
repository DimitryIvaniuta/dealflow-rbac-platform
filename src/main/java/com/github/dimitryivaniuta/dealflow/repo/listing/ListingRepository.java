package com.github.dimitryivaniuta.dealflow.repo.listing;

import com.github.dimitryivaniuta.dealflow.domain.listing.Listing;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ListingRepository extends JpaRepository<Listing, UUID>, JpaSpecificationExecutor<Listing> {
}
