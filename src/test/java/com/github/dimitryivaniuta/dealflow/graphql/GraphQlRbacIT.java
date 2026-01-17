package com.github.dimitryivaniuta.dealflow.graphql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dimitryivaniuta.dealflow.domain.security.RoleKey;
import com.github.dimitryivaniuta.dealflow.domain.workspace.MemberRole;
import com.github.dimitryivaniuta.dealflow.domain.workspace.MemberStatus;
import com.github.dimitryivaniuta.dealflow.domain.workspace.Workspace;
import com.github.dimitryivaniuta.dealflow.domain.workspace.WorkspaceMember;
import com.github.dimitryivaniuta.dealflow.infra.BasePostgresIT;
import com.github.dimitryivaniuta.dealflow.repo.security.RoleRepository;
import com.github.dimitryivaniuta.dealflow.repo.workspace.MemberRoleRepository;
import com.github.dimitryivaniuta.dealflow.repo.workspace.WorkspaceMemberRepository;
import com.github.dimitryivaniuta.dealflow.repo.workspace.WorkspaceRepository;
import java.util.Map;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
class GraphQlRbacIT extends BasePostgresIT {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @Autowired WorkspaceRepository workspaceRepository;
    @Autowired WorkspaceMemberRepository memberRepository;
    @Autowired RoleRepository roleRepository;
    @Autowired MemberRoleRepository memberRoleRepository;

    @Test
    void admin_can_create_customer_and_query_with_spec_filter() throws Exception {
        UUID wsId = demoWorkspaceId();

        // create customer
        String mutation = """
            mutation Create($input: CreateCustomerInput!) {
              createCustomer(input: $input) { id displayName status }
            }
        """;

        Map<String, Object> variables = Map.of(
            "input", Map.of(
                "workspaceId", wsId.toString(),
                "displayName", "Acme Holdings",
                "email", "hello@acme.test"
            )
        );

        mvc.perform(post("/graphql")
                .with(jwt().jwt(j -> j.subject("user-1")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(graphqlBody(mutation, variables)))
            .andExpect(status().isOk());

        // query with filter
        String query = """
            query Q($ws: UUID!, $filter: CustomerFilterInput, $page: Int!, $size: Int!) {
              customers(workspaceId: $ws, filter: $filter, page: $page, size: $size) {
                content { displayName status }
                totalElements
              }
            }
        """;

        Map<String, Object> vars2 = Map.of(
            "ws", wsId.toString(),
            "filter", Map.of("text", "acme"),
            "page", 0,
            "size", 10
        );

        String resp = mvc.perform(post("/graphql")
                .with(jwt().jwt(j -> j.subject("user-1")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(graphqlBody(query, vars2)))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        assertThat(resp).contains("Acme Holdings");
    }

    @Test
    void viewer_cannot_create_customer() throws Exception {
        UUID wsId = demoWorkspaceId();

        ensureViewerMember(wsId, "user-viewer");

        String mutation = """
            mutation Create($input: CreateCustomerInput!) {
              createCustomer(input: $input) { id }
            }
        """;

        Map<String, Object> variables = Map.of(
            "input", Map.of(
                "workspaceId", wsId.toString(),
                "displayName", "Forbidden Corp"
            )
        );

        // Method security should block -> 403
        mvc.perform(post("/graphql")
                .with(jwt().jwt(j -> j.subject("user-viewer")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(graphqlBody(mutation, variables)))
            .andExpect(status().isForbidden());
    }

    private UUID demoWorkspaceId() {
        Workspace ws = workspaceRepository.findBySlug("demo").orElseThrow();
        return ws.getId();
    }

    @SneakyThrows
    private String graphqlBody(String query, Map<String, Object> variables) {
        return om.writeValueAsString(Map.of("query", query, "variables", variables));
    }

    private void ensureViewerMember(UUID wsId, String subject) {
        Workspace ws = workspaceRepository.findById(wsId).orElseThrow();

        WorkspaceMember member = memberRepository.findByWorkspaceAndSubject(wsId, subject).orElse(null);
        if (member == null) {
            member = new WorkspaceMember();
            member.setWorkspace(ws);
            member.setSubject(subject);
            member.setEmail(subject + "@example.com");
            member.setDisplayName("Viewer User");
            member.setStatus(MemberStatus.ACTIVE);
            member = memberRepository.save(member);
        }

        var viewerRole = roleRepository.findByRoleKey(RoleKey.VIEWER).orElseThrow();

        boolean already = memberRoleRepository.findAll().stream()
            .anyMatch(mr -> mr.getMember().getId().equals(member.getId()) && mr.getRole().getId().equals(viewerRole.getId()));

        if (!already) {
            MemberRole mr = new MemberRole();
            mr.setMember(member);
            mr.setRole(viewerRole);
            memberRoleRepository.save(mr);
        }
    }
}
