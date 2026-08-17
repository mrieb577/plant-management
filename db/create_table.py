import mariadb
import db_vars

try:
    connection = mariadb.connect(
        user=db_vars.user,
        password=db_vars.password,
        host=db_vars.host,
        port=db_vars.port,
        database=db_vars.database
    )
except mariadb.Error as e:
    print(f"Error connecting to MariaDB Platform: {e}")
    sys.exit(1)

create_user_table_query = """
create table userdata (
    user_id int auto_increment primary key,
    username varchar(64) not null,
    email varchar(128) not null unique,
    password_hash varchar(255) not null,
    date_joined date default current_date()
);
"""

create_table_query = """
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
"""

cursor = connection.cursor()
cursor.execute(create_user_table_query)
cursor.execute(create_table_query)

connection.commit()
connection.close()