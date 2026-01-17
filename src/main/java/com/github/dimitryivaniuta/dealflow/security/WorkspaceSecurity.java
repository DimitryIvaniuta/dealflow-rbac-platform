package com.github.dimitryivaniuta.dealflow.security;

import com.github.dimitryivaniuta.dealflow.domain.security.PermissionCode;
import com.github.dimitryivaniuta.dealflow.domain.workspace.MemberStatus;
import com.github.dimitryivaniuta.dealflow.domain.workspace.WorkspaceMember;
import com.github.dimitryivaniuta.dealflow.repo.workspace.MemberRoleRepository;
import com.github.dimitryivaniuta.dealflow.repo.workspace.WorkspaceMemberRepository;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("wsSec")
@RequiredArgsConstructor
public class WorkspaceSecurity {

    private final WorkspaceMemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Transactional(readOnly = true)
    public boolean hasPermission(UUID workspaceId, PermissionCode permission) {
        Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }

        String subject = auth.getName();

        WorkspaceMember member = memberRepository.findByWorkspaceAndSubject(workspaceId, subject)
            .orElse(null);

        if (member == null || member.getStatus() != MemberStatus.ACTIVE) {
            return false;
        }

        Set<PermissionCode> perms = memberRoleRepository.findPermissionCodesForMember(member.getId());
        return perms.contains(permission) || perms.contains(PermissionCode.WORKSPACE_ADMIN);
    }
}
