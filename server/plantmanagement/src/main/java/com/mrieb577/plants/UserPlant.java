package com.mrieb577.plants;

import java.util.HashMap;

public class UserPlant extends HashMap<String, String> {

    
    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof UserPlant)) return false;
        UserPlant p = (UserPlant) o;
        return p.get("user_plant_id").equals(get("user_plant_id"));
    }
}
