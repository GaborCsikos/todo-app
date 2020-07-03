delete from user_role;
delete from user;
delete from role;

insert into user(id,username,password) values
(1, 'admin', '$2a$10$EB1JoedJ5RoTj84wxxgPPePBNJLGdwvwOzPyoVVwH2nbmte6ZwPpq');

insert into role(id,name) values
(1, 'ADMIN');

insert into user_role(user_id,role_id) values
(1, 1);