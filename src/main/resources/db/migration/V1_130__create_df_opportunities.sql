create table if not exists df_opportunities (
    id uuid primary key default gen_random_uuid(),
    row_version bigint not null default 0,
    created_at timestamptz not null default now(),
    created_by varchar(128) not null default 'system',
    updated_at timestamptz not null default now(),
    updated_by varchar(128) not null default 'system',

    workspace_id uuid not null references df_workspaces(id) on delete cascade,
    title varchar(220) not null,
    amount numeric(19,2),
    stage varchar(32) not null,
    expected_close_date date,
    customer_id uuid references df_customers(id),
    owner_member_id uuid references df_workspace_members(id)
);

create index if not exists ix_df_opps_ws on df_opportunities(workspace_id);
create index if not exists ix_df_opps_stage on df_opportunities(stage);
create index if not exists ix_df_opps_owner on df_opportunities(owner_member_id);
