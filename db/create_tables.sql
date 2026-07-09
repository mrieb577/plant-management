create table plants (
    plant_id int auto_increment primary key,
    symbol varchar(8) not null,
    scientific_name varchar(128) not null,
    author varchar(128),
    common_name varchar(128),
    family varchar(64),
    image_url varchar(255),
    sun_requirement enum('full sun', 'partial sun', 'partial shade', 'shade') not null,
    water_requirement enum('very high', 'high', 'medium', 'low', 'very low') not null
);

create table userdata (
    user_id int auto_increment primary key,
    username varchar(64) not null,
    email varchar(128) not null unique,
    password_hash varchar(255) not null,
    date_joined date default current_date()
);

create table userplants (
    user_plant_id int auto_increment primary key,
    nickname varchar(255),
    notes text,
    is_indoor boolean default false,
    water_interval int default 7,
    water_quantity_ml int,
    last_watered date default current_date(),
    fertilize_interval int default 0,
    last_fertilized date,
    date_acquired date default current_date(),
    location varchar(128),
    user_id int not null,
    plant_id int not null,
    constraint fk_user foreign key (user_id) references userdata(user_id),
    constraint fk_plant foreign key (plant_id) references plants(plant_id)
);


cursor.execute("""create table plants (
    plant_id int auto_increment primary key,
    symbol varchar(8) not null,
    scientific_name varchar(128) not null,
    author varchar(128),
    common_name varchar(128),
    family varchar(64),
    image_url varchar(255),
    sun_requirement enum('full sun', 'partial sun', 'partial shade', 'shade') not null,
    water_requirement enum('very high', 'high', 'medium', 'low', 'very low') not null
);
""")