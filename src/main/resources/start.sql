-- roles
insert into role(role_id,role) value (1, 'ADMIN');
insert into role(role_id, role) value (2, 'USER');

-- users
insert into  user_app (id, active,email,name,last_name,login,password)
value (1,1,'lucjan@gmail.com','Lucjan','Pavarotti','pawcio','$2a$10$Q2o3qfUcMraNmZSEcqxYweomGIH8pSlGuMGelVxXYNf/nf2WpVYSC');
insert into  user_app (id, active,email,name,last_name,login,password)
    value (2,1,'lucjan2@gmail.com','Adam','Pavarotti','adam','$2a$10$kM6Vx1qM9ym8hBPdZZn4yePcigiG9dumX4joOzUBSTq0yDMvF9KbK');
-- add roles to users
insert into user_role (user_id, role_id) value (1,2);
insert into user_role (user_id, role_id) value (1,1);
insert into user_role (user_id, role_id) value (2,2);