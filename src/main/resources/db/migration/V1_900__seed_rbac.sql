-- System roles
insert into df_roles (role_key, display_name, is_system)
values
 ('OWNER', 'Workspace Owner', true),
 ('ADMIN', 'Workspace Admin', true),
 ('AGENT', 'Sales Agent', true),
 ('VIEWER', 'Read-only Viewer', true)
on conflict (role_key) do nothing;

-- Permissions
insert into df_permissions (code, description)
values
 ('WORKSPACE_ADMIN', 'Full workspace administration'),
 ('CUSTOMER_READ', 'Read customers'),
 ('CUSTOMER_WRITE', 'Create/update customers'),
 ('LISTING_READ', 'Read listings'),
 ('LISTING_WRITE', 'Create/update listings'),
 ('PIPELINE_READ', 'Read opportunities'),
 ('PIPELINE_WRITE', 'Create/update opportunities')
on conflict (code) do nothing;

-- Role → permissions mapping
with r as (
    select id, role_key from df_roles
),
p as (
    select id, code from df_permissions
)
insert into df_role_permissions(role_id, permission_id)
select r.id, p.id
from r join p on (
    (r.role_key = 'OWNER' and p.code in ('WORKSPACE_ADMIN','CUSTOMER_READ','CUSTOMER_WRITE','LISTING_READ','LISTING_WRITE','PIPELINE_READ','PIPELINE_WRITE'))
 or (r.role_key = 'ADMIN' and p.code in ('WORKSPACE_ADMIN','CUSTOMER_READ','CUSTOMER_WRITE','LISTING_READ','LISTING_WRITE','PIPELINE_READ','PIPELINE_WRITE'))
 or (r.role_key = 'AGENT' and p.code in ('CUSTOMER_READ','CUSTOMER_WRITE','LISTING_READ','LISTING_WRITE','PIPELINE_READ','PIPELINE_WRITE'))
 or (r.role_key = 'VIEWER' and p.code in ('CUSTOMER_READ','LISTING_READ','PIPELINE_READ'))
)
on conflict do nothing;
