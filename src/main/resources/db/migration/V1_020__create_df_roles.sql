create table if not exists df_roles (
    id uuid primary key default gen_random_uuid(),
    row_version bigint not null default 0,
    created_at timestamptz not null default now(),
    created_by varchar(128) not null default 'system',
    updated_at timestamptz not null default now(),
    updated_by varchar(128) not null default 'system',

    role_key varchar(32) not null unique,
    display_name varchar(120) not null,
    is_system boolean not null
);
