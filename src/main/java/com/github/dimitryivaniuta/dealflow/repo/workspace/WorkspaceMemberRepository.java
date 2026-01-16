package com.github.dimitryivaniuta.dealflow.repo.workspace;

import com.github.dimitryivaniuta.dealflow.domain.workspace.WorkspaceMember;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, UUID> {

    @Query(
        "select m " +
        "from WorkspaceMember m " +
        "where m.workspace.id = :workspaceId " +
        "  and m.subject = :subject"
    )
    Optional<WorkspaceMember> findByWorkspaceAndSubject(@Param("workspaceId") UUID workspaceId, @Param("subject") String subject);
}
