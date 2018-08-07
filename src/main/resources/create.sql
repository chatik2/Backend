drop table if exists hibernate_sequence;
drop table if exists messages;
drop table if exists usr;
create table hibernate_sequence (next_val bigint) engine=MyISAM;
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
create table messages (id bigint not null, date datetime, text varchar(255), user_id bigint, primary key (id)) engine=MyISAM;
create table usr (id bigint not null, name varchar(255), primary key (id)) engine=MyISAM;
alter table messages add constraint FK5f19dxsguyxb310o6hn9ccmbt foreign key (user_id) references usr (id);

INSERT INTO `chatik`.`usr` (`id`, `name`) VALUES (1, 'user1');
INSERT INTO `chatik`.`usr` (`id`, `name`) VALUES (2, 'user2');
INSERT INTO `chatik`.`messages` (`id`, `date`, `text`, `user_id`) VALUES (1, '2018-08-03 14:22:33', 'text1', 1);
INSERT INTO `chatik`.`messages` (`id`, `date`, `text`, `user_id`) VALUES (2, '2018-08-03 14:22:56', 'text2', 1);
INSERT INTO `chatik`.`messages` (`id`, `date`, `text`, `user_id`) VALUES (3, '2018-08-03 14:23:00', 'text3', 2);
INSERT INTO `chatik`.`messages` (`id`, `date`, `text`, `user_id`) VALUES (4, '2018-08-03 14:23:02', 'text4', 2);

alter table chatik.usr add column second_id bigint;
alter table messages add constraint FK5f19dxsguyxb310o6hn9ccmbt foreign key (user_id) references usr (id);