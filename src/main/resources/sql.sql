
create table authorities (
                             user_id bigint not null,
                             authority varchar(30)
) engine=InnoDB;

create table users (
                       id bigint not null auto_increment,
                       accountNonExpired bit not null,
                       accountNonLocked bit not null,
                       credentialsNonExpired bit not null,
                       enabled bit not null,
                       password varchar(80),
                       username varchar(70),
                       primary key (id)
) engine=InnoDB;

alter table authorities
    add constraint FKk91upmbueyim93v469wj7b2qh
        foreign key (user_id)
            references users (id)