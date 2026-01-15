create table if not exists df_permissions (
    id uuid primary key default gen_random_uuid(),
    row_version bigint not null default 0,
    created_at timestamptz not null default now(),
    created_by varchar(128) not null default 'system',
    updated_at timestamptz not null default now(),
    updated_by varchar(128) not null default 'system',

    code varchar(64) not null unique,
    description varchar(200) not null
);
