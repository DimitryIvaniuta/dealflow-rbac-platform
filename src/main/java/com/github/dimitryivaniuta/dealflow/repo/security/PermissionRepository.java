package com.github.dimitryivaniuta.dealflow.repo.security;

import com.github.dimitryivaniuta.dealflow.domain.security.Permission;
import com.github.dimitryivaniuta.dealflow.domain.security.PermissionCode;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByCode(PermissionCode code);
}
