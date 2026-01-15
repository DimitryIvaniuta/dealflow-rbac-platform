create table if not exists df_role_permissions (
    id uuid primary key default gen_random_uuid(),
    row_version bigint not null default 0,
    created_at timestamptz not null default now(),
    created_by varchar(128) not null default 'system',
    updated_at timestamptz not null default now(),
    updated_by varchar(128) not null default 'system',

    role_id uuid not null references df_roles(id) on delete cascade,
    permission_id uuid not null references df_permissions(id) on delete cascade,

    constraint uq_df_role_permissions_role_perm unique (role_id, permission_id)
);

create index if not exists ix_df_role_permissions_role on df_role_permissions(role_id);
create index if not exists ix_df_role_permissions_perm on df_role_permissions(permission_id);
