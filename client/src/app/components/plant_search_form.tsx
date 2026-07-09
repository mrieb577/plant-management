import axios from "axios";
import { useState } from "react";
import PlantDisplay from "./plant_display";
import type { Plant } from "../data/plant";
import "./plant_search_form.css"
import { AddPlantForm } from "./add_plant_form";

export default function PlantSearchForm(){
  const [message, setMessage] = useState("");
  const [results, setResults] = useState<Plant[]>([]);
  const [resultSize, setResultSize] = useState(0);
  const [search, setSearch] = useState("");

  const [resultIndex, setResultIndex] = useState(0);

  return (<div>
      <p>{message} </p>
      <PlantDisplay pd={results ? results[resultIndex] : null}></PlantDisplay>
      <button onClick={() => {
        if(resultIndex > 0) setResultIndex(resultIndex - 1);
        if(resultIndex <= 0) setResultIndex(resultSize - 1);
      }}>&lt;&lt;</button>
      <span className="resultIndex">{resultIndex + 1}/{resultSize}</span>
      <button onClick={() => {
        if(resultIndex < resultSize) setResultIndex(resultIndex + 1);
        if(resultIndex >= resultSize - 1) setResultIndex(0);
      }}>&gt;&gt;</button>

      <AddPlantForm pd={results ? results[resultIndex] : null}></AddPlantForm>

      <p className="title">Search</p>
      <input type='text' onChange={(e) => {
        setSearch(e.target.value);
      }}></input>

      <button onClick={() => {
        axios.get(`http://localhost:8080/plant?search=${search}`).then((response) => {
          console.log(response.data);
          setResults(response.data.results as Plant[]);
          setResultSize(response.data.count);
          setResultIndex(0);
        }).catch((err) => {setMessage(err.message)});
      }}>Search</button>
    </div>
  );
}
