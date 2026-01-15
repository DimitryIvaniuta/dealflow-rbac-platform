-- Demo workspace + demo member mapping.
-- Subject should match your JWT 'sub' (or authentication name in tests).
insert into df_workspaces (slug, display_name, status)
values ('demo', 'Demo Workspace', 'ACTIVE')
on conflict (slug) do nothing;

with ws as (select id from df_workspaces where slug = 'demo')
insert into df_workspace_members (workspace_id, subject, email, display_name, status)
select ws.id, 'user-1', 'user-1@example.com', 'Demo User', 'ACTIVE'
from ws
on conflict (workspace_id, subject) do nothing;

-- Assign ADMIN role to demo member
with m as (
    select id as member_id from df_workspace_members where subject = 'user-1'
),
r as (
    select id as role_id from df_roles where role_key = 'ADMIN'
)
insert into df_member_roles(member_id, role_id)
select m.member_id, r.role_id
from m, r
on conflict do nothing;
