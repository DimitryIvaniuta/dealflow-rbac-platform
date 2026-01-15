create table if not exists df_member_roles (
    id uuid primary key default gen_random_uuid(),
    row_version bigint not null default 0,
    created_at timestamptz not null default now(),
    created_by varchar(128) not null default 'system',
    updated_at timestamptz not null default now(),
    updated_by varchar(128) not null default 'system',

    member_id uuid not null references df_workspace_members(id) on delete cascade,
    role_id uuid not null references df_roles(id) on delete cascade,

    constraint uq_df_member_roles_member_role unique (member_id, role_id)
);

create index if not exists ix_df_member_roles_member on df_member_roles(member_id);
create index if not exists ix_df_member_roles_role on df_member_roles(role_id);
