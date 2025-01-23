do
$$
    begin
        if not exists(select from pg_type where typname = 'sex') then
            create type "sex" as enum ('MALE','FEMALE');
        end if;
    end
$$;

create table if not exists student
(
    id         varchar primary key,
    name       varchar,
    sex        sex,
    birth_date date
);