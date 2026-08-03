import { Plant } from "../../data/plant";

interface PlantListParams {
  plants : Plant[] | null
}

export default function PlantList({ plants } : PlantListParams){
  return (
    <div>
      <p>There should be a list of plants here</p>
    </div>
  )
}
