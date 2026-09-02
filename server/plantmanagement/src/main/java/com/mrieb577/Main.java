package com.mrieb577;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mrieb577.database.DatabaseConnection;
import com.mrieb577.database.PlantsDB;
import com.mrieb577.plants.PlantQueryResult;
import com.mrieb577.plants.Plants;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
