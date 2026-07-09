package com.mrieb577;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mrieb577.database.DatabaseConnection;
import com.mrieb577.database.PlantsDB;
import com.mrieb577.objects.PlantQueryResult;
import com.mrieb577.objects.Plants;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // https://spring.io/guides/gs/rest-service-cors
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/plant")
    public String plant(@RequestParam(value = "search", defaultValue = "") String search){
        Gson gson = new Gson();
        DatabaseConnection db = new DatabaseConnection();
        Plants ps;
        if(search.length() < 2){
            ps = PlantsDB.fetch_all(db);
        } else {
            ps = PlantsDB.search_for_plant(db, search);
            if(ps.size() == 0) ps = PlantsDB.fetch_all(db);
        }
        db.close();
        PlantQueryResult p = new PlantQueryResult(ps);
        return gson.toJson(p);
    }
}
