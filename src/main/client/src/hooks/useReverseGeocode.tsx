import {useState} from 'react';

// use reverse geocoding to get timezone

const GEOAPIFY_API_KEY = import.meta.env.VITE_GEOAPIFY_REVERSE_GEOCODING_KEY;
const GEOAPIFY_REVERSE_GEOCODING_URL = import.meta.env.VITE_GEOAPIFY_REVERSE_GEOCODING_URL;

const useReverseGeocode = () => {
    const [timezone,setTimezone] = useState<string>('');
    const [error,setError] = useState<string | null>(null);

    const fetchTimeZone = async(latitude: string, longitude:string) => {
        try{ 
            const url = `${GEOAPIFY_REVERSE_GEOCODING_URL}?lat=${latitude}&lon=${longitude}&apiKey=${GEOAPIFY_API_KEY}`
            console.log(url)
            const response = await fetch(
                url
                
            );
        
        if (!response.ok) {
            throw new Error(`HTTP ERROR - status: ${response.status}`);
        }
        const data = await response.json();

        console.log('Geoapify response data: ', data);


        if(data.features.length > 0) {
            // safely access with ? even if null or undefined
            // timezone object gives offset from DST or STD - grab the STD offset
            const timezone = data.features[0]?.properties?.timezone?.offset_STD;
            console.log('timezone:', timezone);
            console.log('data:', data);
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
    return ({timezone,fetchTimeZone,error} )
}

export default useReverseGeocode