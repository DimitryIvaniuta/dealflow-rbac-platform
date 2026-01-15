create table if not exists df_listings (
    id uuid primary key default gen_random_uuid(),
    row_version bigint not null default 0,
    created_at timestamptz not null default now(),
    created_by varchar(128) not null default 'system',
    updated_at timestamptz not null default now(),
    updated_by varchar(128) not null default 'system',

    workspace_id uuid not null references df_workspaces(id) on delete cascade,
    title varchar(220) not null,
    city varchar(120) not null,
    city_normalized varchar(120) not null,
    asking_price numeric(19,2) not null,
    status varchar(32) not null,
    customer_id uuid references df_customers(id)
);

create index if not exists ix_df_listings_ws on df_listings(workspace_id);
create index if not exists ix_df_listings_status on df_listings(status);
create index if not exists ix_df_listings_city on df_listings(city_normalized);
