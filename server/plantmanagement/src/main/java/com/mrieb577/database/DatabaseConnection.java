package com.mrieb577.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrieb577.objects.Plant;

public class DatabaseConnection {
    private final String COLUMNS = "symbol,synonym_symbol,scientific_name,author,common_name,family";

    private static Logger log = LoggerFactory.getLogger(DatabaseConnection.class);
    private Connection connection;

    public DatabaseConnection(){
        Credentials creds = new Credentials();
        try {
            connection = DriverManager.getConnection(creds.getURL());
        } catch (SQLException e) {
            log.error("Unable to connect to database - {}", e);
        }
    }

    public Plant query(String query){
        String[] cols = COLUMNS.split(",");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                Plant plant = new Plant();
                for(String col : cols){
                    plant.put(col, resultSet.getString(col));
                }
                return plant;
            }
        } catch (SQLException e) {
            log.error("Unable to execute query - {}", e);
        }
        return null;
    }
}
