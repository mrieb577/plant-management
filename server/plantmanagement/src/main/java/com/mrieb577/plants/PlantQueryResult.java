package com.mrieb577.plants;

public class PlantQueryResult {
    public Plants results;
    public int count;

    public PlantQueryResult(Plants res){
        results = res;
        count = res.size();
    }
}
