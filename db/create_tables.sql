create table plants (
    plant_id int auto_increment primary key,
    symbol varchar(8) not null,
    synonym_symbol varchar(8),
    scientific_name varchar(128) not null,
    author varchar(255),
    common_name varchar(128),
    family varchar(64),
);

create table userplants (
    user_plant_id int primary key,
    nickname varchar(255),
    is_indoor boolean default false,
    water_interval int default 7,
    water_quantity_ml int,
    last_watered date default current_date(),
    date_acquired date default current_date(),
    user_id int not null,
    plant_id int not null,
    constraint fk_user foreign key (user_id) references userdata(user_id),
    constraint fk_plant foreign key (plant_id) references plants(plant_id),
);

create table userdata (
    user_id int primary key,
    username varchar(64) not null,
    user_password varchar(64) not null,
    date_joined date default current_date(),

);