export interface Vehicle {
  id?: number;
  vin: string;
  make: string;
  model: string;
  year: number;
  trim?: string;
  doors?: number;
  color?: string;
  fuel?: string;
  vehicleType?: string;
  cityMpg?: number;
  highwayMpg?: number;
  combinedMpg?: number;
  transmission?: string;
}