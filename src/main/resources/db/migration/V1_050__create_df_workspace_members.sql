create table if not exists df_workspace_members (
    id uuid primary key default gen_random_uuid(),
    row_version bigint not null default 0,
    created_at timestamptz not null default now(),
    created_by varchar(128) not null default 'system',
    updated_at timestamptz not null default now(),
    updated_by varchar(128) not null default 'system',

    workspace_id uuid not null references df_workspaces(id) on delete cascade,
    subject varchar(128) not null,
    email varchar(180) not null,
    display_name varchar(160) not null,
    status varchar(32) not null,

    constraint uq_df_workspace_members_ws_subject unique (workspace_id, subject)
);

create index if not exists ix_df_workspace_members_ws on df_workspace_members(workspace_id);
create index if not exists ix_df_workspace_members_subject on df_workspace_members(subject);
create index if not exists ix_df_workspace_members_status on df_workspace_members(status);
