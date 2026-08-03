export type Plant = {
  plant_id: number;
  symbol: string;
  scientific_name: string;
  author: string;
  common_name: string;
  family: string;
  image_url: string;
  sun_requirement: string;
  water_requirement: string;
}


export interface PlantParameter {
  pd: Plant | null;
}
