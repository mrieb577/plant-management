import mariadb
import db_vars
import sys
import tqdm
import sqlite3
import pandas as pd
import math
import re

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

insert_query = """
    insert into plants (symbol, scientific_name, author, common_name, family, image_url, sun_requirement, water_requirement) 
    values (?, ?, ?, ?, ?, ?, ?, ?)
"""

conn = sqlite3.connect("plants.db")
df = pd.read_sql_query("select * from plants", conn)
dfc = pd.read_sql_query("select * from plant_characteristics", conn)
dft = pd.read_sql_query("select * from plant_taxonomic_paths", conn)

dfc = dfc.reset_index()
dft = dft.reset_index()
dfc['plant_id'] = dfc['plant_id'].astype(str)
dft['plant_id'] = dft['plant_id'].astype(str)
dfc = dfc.set_index('plant_id')
dft = dft.set_index('plant_id')

sun_reqs = ['unknown', 'full sun', 'partial sun', 'partial shade', 'shade']
water_reqs = ['unknown', 'very high', 'high', 'medium', 'low', 'very low']

def sun_map(val):
    if val == None: return 0
    if val == "nan": return 2
    if val == "Tolerant": return 4
    if val == "Intermediate": return 2
    if val == "Intolerant": return 1

def water_map(min, max):
    if(math.isnan(min) or math.isnan(max)): return 0
    avg = (min + max) / 2
    if avg < 24: return 5
    if avg < 40: return 4
    if avg < 50: return 3
    if avg < 64: return 2
    return 1

missing_chars = 0

for i in tqdm.tqdm(range(df.shape[0])):
    entry = df.iloc[i]
    plant_id = str(entry.id)

    if plant_id in dfc.index:
        chars = dfc.loc[plant_id]
        shade = chars.shade_tolerance
        sun_requirement = sun_map(shade if isinstance(shade, str) else "nan")
        water_requirement = water_map(float(chars.precipitation_minimum), float(chars.precipitation_maximum))
    else:
        missing_chars += 1
        sun_requirement = 0
        water_requirement = 0

    taxon = dft.loc[plant_id]
    family = re.search(r"(?<=<i>)(.*?)(?=</i>)", taxon.family).group().strip()

    common_name = entry.common_name if (type(entry.common_name) != float) else ""
    image_url = entry.profile_image_url if (type(entry.profile_image_url) != float) else ""

    raw = entry.scientific_name
    sci_match = re.search(r"(?<=<i>)(.*?)(?=</i>)", raw)
    author_match = re.search(r"</i>\s*(.*)", raw)
    scientific_name = sci_match.group().strip() if sci_match else raw
    author = author_match.group(1).strip() if author_match else ""
    
    try:
        cursor.execute(insert_query, (
            entry.symbol, 
            scientific_name, 
            author, 
            common_name, 
            family, 
            image_url, 
            sun_reqs[sun_requirement], 
            water_reqs[water_requirement]
        ))
    except mariadb.Error as e:
        print(f"DB error on plant_id {plant_id} ({entry}): {e}")

conn.close()

connection.commit()
connection.close()

print(f"Done. Inserted plants with missing data breakdown:")
print(f"\tMissing characteristics: {missing_chars}")
