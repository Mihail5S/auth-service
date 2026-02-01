create table if not exists users (
    id bigserial primary key,
    email varchar(255) not null unique,
    password_hash varchar(255) not null,
    created_at timestamptz not null default now()
    );

create table if not exists user_roles (
    user_id bigint not null references users(id) on delete cascade,
    role varchar(50) not null,
    primary key (user_id, role)
    );
