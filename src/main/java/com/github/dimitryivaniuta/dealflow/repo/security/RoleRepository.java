package com.github.dimitryivaniuta.dealflow.repo.security;

import com.github.dimitryivaniuta.dealflow.domain.security.Role;
import com.github.dimitryivaniuta.dealflow.domain.security.RoleKey;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleKey(RoleKey roleKey);
}
