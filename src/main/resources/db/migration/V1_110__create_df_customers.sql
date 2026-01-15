create table if not exists df_customers (
    id uuid primary key default gen_random_uuid(),
    row_version bigint not null default 0,
    created_at timestamptz not null default now(),
    created_by varchar(128) not null default 'system',
    updated_at timestamptz not null default now(),
    updated_by varchar(128) not null default 'system',

    workspace_id uuid not null references df_workspaces(id) on delete cascade,
    display_name varchar(200) not null,
    normalized_name varchar(200) not null,
    email varchar(180),
    phone varchar(40),
    external_ref varchar(80),
    status varchar(32) not null,
    owner_member_id uuid references df_workspace_members(id)
);

create index if not exists ix_df_customers_ws on df_customers(workspace_id);
create index if not exists ix_df_customers_status on df_customers(status);
create index if not exists ix_df_customers_name on df_customers(normalized_name);
