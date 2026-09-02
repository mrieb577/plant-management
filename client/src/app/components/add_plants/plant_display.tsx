import type { Plant, PlantParameter } from "../../data/plant";
import "./plant_display.css"

export default function PlantDisplay({ pd } : PlantParameter){
  if(pd != null){
    return(<div>
      <p>
        <b>{pd.family}</b> <br/>
        <i>{pd.scientific_name}</i>: {pd.common_name} <br/>
        Symbol: {pd.symbol}
        <br/> <br/>
        Water requirement: <span className="waterRequirement">{pd.water_requirement}<br/></span>
        Sun requirement: <span className="sunRequirement">{pd.sun_requirement}<br/></span>
        <br/>
        <a href={`https://en.wikipedia.org/wiki/${pd.scientific_name.replace(/\s/g, '_')}`} target="_blank" rel="noopener noreferrer">
          <button>View on Wikipedia</button>
        </a>
      </p>
    </div>);
  } else {
    return (<div>

    </div>)
  }
}
