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
        Author: <i>{pd.author}</i>
      </p>
    </div>);
  } else {
    return (<div>

    </div>)
  }
}
