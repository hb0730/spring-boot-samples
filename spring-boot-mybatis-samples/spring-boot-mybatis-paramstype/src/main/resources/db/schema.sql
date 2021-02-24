drop  table if exists `user`;
create table `user`(
    `id` int not null auto_increment,
    `name` varchar(255),
    `type` varchar(255),
    `deleted` int,
    primary key (`id`)
);
