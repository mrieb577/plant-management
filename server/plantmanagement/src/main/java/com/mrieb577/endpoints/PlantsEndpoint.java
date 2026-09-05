package com.mrieb577.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mrieb577.database.DatabaseConnection;
import com.mrieb577.database.PlantsDB;
import com.mrieb577.database.UserPlantsDB;
import com.mrieb577.database.UsersDB;
import com.mrieb577.login.UserInfo;
import com.mrieb577.plants.UserPlant;
import com.mrieb577.plants.PlantQueryResult;
import com.mrieb577.plants.Plants;

@RestController
@RequestMapping("/plants")
public class PlantsEndpoint {
    private static Logger log = LoggerFactory.getLogger(PlantsEndpoint.class);
    
    @GetMapping("/search")
    public String search(@RequestParam(value = "val", defaultValue = "") String val){
        Gson gson = new Gson();
        DatabaseConnection db = new DatabaseConnection();
        Plants ps;
        if(val.length() < 2){
            ps = PlantsDB.fetch_all(db);
        } else {
            ps = PlantsDB.search_for_plant(db, val);
            if(ps.size() == 0) ps = PlantsDB.fetch_all(db);
        }
        db.close();
        PlantQueryResult p = new PlantQueryResult(ps);
        return gson.toJson(p);
    }

    @PostMapping("/add")
    public String addPlant(@RequestBody UserPlant plant, Authentication authentication){
        DatabaseConnection db = new DatabaseConnection();
        log.info("authentication - {}", authentication.toString());
        UserInfo currentUser = UsersDB.getUserByEmail(db, authentication.getName());
        try {
            return UserPlantsDB.add(db, plant, currentUser);
        } finally {
            db.close();
        }
    }
}
