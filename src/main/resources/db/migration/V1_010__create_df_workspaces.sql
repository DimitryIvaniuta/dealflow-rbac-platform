create table if not exists df_workspaces (
    id uuid primary key default gen_random_uuid(),
    row_version bigint not null default 0,
    created_at timestamptz not null default now(),
    created_by varchar(128) not null default 'system',
    updated_at timestamptz not null default now(),
    updated_by varchar(128) not null default 'system',

    slug varchar(64) not null unique,
    display_name varchar(160) not null,
    status varchar(32) not null
);

create index if not exists ix_df_workspaces_status on df_workspaces(status);
