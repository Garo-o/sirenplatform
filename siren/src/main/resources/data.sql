insert into user(user_id, email, password, name, activated)
values (1,'admin','$2a$10$VhhcvZ1jAetTJlxmaTZDSuFG6bB9a17NgjMEQLRHZ62Ghtpf1uj9q','admin',1);

insert into authority(authority_name) values ('ROLE_USER');
insert into authority(authority_name) values ('ROLE_MANAGER');
insert into authority(authority_name) values ('ROLE_ADMIN');

insert into user_authority (user_id, authority_name) values (1, 'ROLE_USER');
insert into user_authority (user_id, authority_name) values (1, 'ROLE_MANAGER');
insert into user_authority (user_id, authority_name) values (1, 'ROLE_ADMIN');