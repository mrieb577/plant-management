import mariadb
import sys
import tqdm
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

cursor = connection.cursor()
cursor.execute("delete from plants")

insert_query = "insert into plants (symbol, synonym_symbol, scientific_name, author, common_name, family) values (\"{}\", \"{}\", \"{}\", \"{}\", \"{}\", \"{}\")"

with open("plantlst.txt", "r", encoding="utf-8") as plants:
    i = 0
    lines = plants.readlines()
    print(f"Updating {len(lines)} plant entries...")
    for line in tqdm.tqdm(lines):
        if i == 0:
            i += 1
            continue # skip headers
        vals = line.strip().replace("\"", "").split(",")
        symbol = vals[0]
        synonym_symbol = vals[1]
        scientific_name = " ".join(vals[2].split(" ")[0:2])
        author = " ".join(vals[2].split(" ")[2:])
        common_name = vals[3].title()
        family = vals[4]
        # print(insert_query.format(symbol, synonym_symbol, scientific_name, author, common_name, family))
        try:
            cursor.execute(insert_query.format(symbol, synonym_symbol, scientific_name, author, common_name, family))
        except mariadb.Error as e: 
            print(f"Error: {e}")
        i += 1

connection.commit()
connection.close()
