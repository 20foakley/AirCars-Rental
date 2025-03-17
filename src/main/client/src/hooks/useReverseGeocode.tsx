import {useState} from 'react';

const GEOAPIFY_API_KEY = import.meta.env.GEOAPIFY_REVERSE_GEOCODING_KEY;
const GEOAPIFY_REVERSE_GEOCODING_URL = import.meta.env.GEOAPIFY_REVERSE_GEOCODING_URL;

const useReverseGeocode = () => {
    const [timezone,setTimezone] = useState<string | null>(null);
    const [error,setError] = useState<string | null>(null);

    const fetchTimeZone = async(latitude: string, longitude:string) => {
        try{ 
            const response = await fetch(
                `${GEOAPIFY_REVERSE_GEOCODING_URL}?lat=${latitude}&lon=${longitude}&apiKey=${GEOAPIFY_API_KEY}`
            );
        
        if (!response.ok) {
            throw new Error('HTTP ERROR - status: ${response.status}');
        }
        const data = await response.json();

        console.log('Geoapify response data: ', data);

        if(data.features.length > 0) {
            // safely access with ? even if null or undefined
            const timezone = data.features[0]?.properties?.timezone?.name;
            setTimezone(timezone);
        }
        else {
            setError('No timezone found for the location');
        }
        } catch(error: any){
            setError(error.message);
            console.error('Failed to fetch timezone:',error);
        }

        }
    }


  return ({timezone,fetchTimeZone,error} )
};

export default useReverseGeocode