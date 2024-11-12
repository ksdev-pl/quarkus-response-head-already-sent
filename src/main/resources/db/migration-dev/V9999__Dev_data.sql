-- password: "secret"
insert into app_user(id, uuid, version, creation_date, modification_date, username, password) values
    (nextval('app_user_seq'), uuid_generate_v4(), 0, current_timestamp, current_timestamp, 'admin@example.com', '$2a$10$v0NEa1T1uDCWw7r8T9qgQOo8Q.YkRonKIN0RTc0Jf5GyF.o9GKZIm'),
    (nextval('app_user_seq'), uuid_generate_v4(), 0, current_timestamp, current_timestamp, 'user@example.com', '$2a$10$v0NEa1T1uDCWw7r8T9qgQOo8Q.YkRonKIN0RTc0Jf5GyF.o9GKZIm');

insert into app_user(id, uuid, version, creation_date, modification_date, username, password)
select
    nextval('app_user_seq'),
    uuid_generate_v4(),
    0,
    current_timestamp,
    current_timestamp,
    'user' || i || '@example.com',
    '$2a$10$v0nea1t1udcww7r8t9qgqoo8q.ykronkin0rtc0jf5gyf.o9gkzim'
from generate_series(1, 50) i;

insert into app_user_role (user_id, role_id) values
    ((select id from app_user where username = 'admin@example.com'), (select id from role where name = 'admin')),
    ((select id from app_user where username = 'user@example.com'), (select id from role where name = 'user'));