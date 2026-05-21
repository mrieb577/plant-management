package com.mrieb577;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mrieb577.database.DatabaseConnection;
import com.mrieb577.objects.Plant;

@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // https://spring.io/guides/gs/rest-service-cors
    @CrossOrigin(origins = "http://localhost:1212")
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @CrossOrigin(origins = "http://localhost:1212")
    @GetMapping("/plant")
    public String plant(){
        Gson gson = new Gson();
        DatabaseConnection db = new DatabaseConnection();
        Plant p = db.query("select * from plants limit 1;");
        return gson.toJson(p);
    }
}