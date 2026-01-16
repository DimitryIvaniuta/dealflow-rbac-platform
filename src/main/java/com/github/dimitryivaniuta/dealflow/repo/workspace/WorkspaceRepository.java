package com.github.dimitryivaniuta.dealflow.repo.workspace;

import com.github.dimitryivaniuta.dealflow.domain.workspace.Workspace;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {
    Optional<Workspace> findBySlug(String slug);
}
