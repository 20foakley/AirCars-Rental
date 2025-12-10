import {Vehicle} from '../types/vehicle';
import apiClient from './apiClient';

const VPIC_URL = import.meta.env.VPIC_URL;


export const addVehicle = async(vehicle: Partial<Vehicle>) => {
    const res = await apiClient.post('/vehicles', vehicle);
    return res.data;
}

export const decodeVin = async(vin: string, modelYear: number): Promise<Partial<Vehicle>> => {
    let url = `${VPIC_URL}${vin}?format=json&modelyear=${modelYear}`;
    try {
        const res = await apiClient.get(url);
        const vehicleJson = res?.data?.Results[0]; // We need to index 0 to get inside the returned array of [ {key/value pairs we need} ]
        if (!vehicleJson) return {};
        const vehicle: Partial<Vehicle> = {
            vin: vehicleJson.VIN,
            make: vehicleJson.Make,
            model: vehicleJson.Model,
            year: Number(vehicleJson.ModelYear),
            trim: vehicleJson.Trim,
            doors: vehicleJson.Doors ? Number(vehicleJson.Doors) : undefined,
            fuel: vehicleJson.FuelTypePrimary || undefined,
            vehicleType: vehicleJson.VehicleType,
            transmission: vehicleJson.TransmissionStyle,
        }
        return vehicle;
    }
    catch(error){
        console.error(`Error decoding vin ${vin} with error: `, error);
        return {};
    }


    
}
