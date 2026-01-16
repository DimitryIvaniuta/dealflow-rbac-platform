package com.github.dimitryivaniuta.dealflow.repo.workspace;

import com.github.dimitryivaniuta.dealflow.domain.security.PermissionCode;
import com.github.dimitryivaniuta.dealflow.domain.workspace.MemberRole;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRoleRepository extends JpaRepository<MemberRole, UUID> {

    @Query(
        "select distinct p.code " +
        "from MemberRole mr " +
        "join mr.role r " +
        "join RolePermission rp on rp.role = r " +
        "join rp.permission p " +
        "where mr.member.id = :memberId"
    )
    Set<PermissionCode> findPermissionCodesForMember(@Param("memberId") UUID memberId);
}
