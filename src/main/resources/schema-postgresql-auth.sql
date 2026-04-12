create table if not exists tb_login_attempts (
    user_id bigint primary key,
    failure_count integer not null default 0,
    window_expires_at timestamp,
    locked_until timestamp
);

create table if not exists tb_refresh_tokens (
    refresh_token varchar(512) primary key,
    user_id bigint not null,
    expires_at timestamp not null,
    constraint fk_refresh_tokens_user
        foreign key (user_id) references tb_users(id)
        on delete cascade
);

create index if not exists idx_refresh_tokens_user_id on tb_refresh_tokens(user_id);
create index if not exists idx_refresh_tokens_expires_at on tb_refresh_tokens(expires_at);
