import React, { useState, useRef, useEffect } from 'react';
import Select from 'react-select';
import useUserLocation from '../hooks/useUserLocation';
import useReverseGeocode from '../hooks/useReverseGeocode';
import { FaSearch } from 'react-icons/fa';

// todo - implement a map overview that shows locations of all available listings matching search criteria, centered at selected location

const NOMINATIM_BASE_URL = "https://nominatim.openstreetmap.org/search?format=json&addressdetails=1&limit=5&q=";

interface HeroListingFinderProps {
  onSearch: ({latitude, longitude, startDateTime, endDateTime, timezone}: {
    latitude: string;
    longitude: string;
    startDateTime: string;
    endDateTime: string;
    timezone: string;
  }) => void;
}

const HeroListingFinder = ({ onSearch }: HeroListingFinderProps) => {
  const [formData, setFormData] = useState({
    fromDate: '',
    fromTime: '',
    untilDate: '',
    untilTime: '',
    timezone: ''
  });

  const [latitude, setLatitude] = useState<string>('');
  const [longitude, setLongitude] = useState<string>('');

  interface LocationOption {
    label: string;
    value: string;
    latitude: string;
    longitude: string;
  }

  const [locationOptions, setLocationOptions] = useState<LocationOption[]>([]);
  const timeoutRef = useRef<ReturnType<typeof setTimeout> | null>(null);
  const { location } = useUserLocation(); // this doesn't do anything super meaningful yet - but it does grab your lat/lon!

  // Hooks here
  const {fetchTimeZone,timezone,error} = useReverseGeocode();

  const handleLocationSearch = (inputValue: string) => {
    if (!inputValue) return;

    if (timeoutRef.current) {
      clearTimeout(timeoutRef.current);
    }

    timeoutRef.current = setTimeout(async () => {
      try {
        let url = `${NOMINATIM_BASE_URL}${encodeURIComponent(inputValue)}`;
        if (location) {
          url += `&lat=${location.lat}&lon=${location.lon}&zoom=10`;
        }

        const response = await fetch(url);
        if (!response.ok) {
          throw new Error(`Nominatim HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();

        if (!Array.isArray(data)) {
          console.error("Nominatim API returned unexpected response format", data);
          return;
        }

        const options: LocationOption[] = data.map((place: any) => ({
          label: place.display_name,
          value: place.display_name,
          latitude: place.lat,
          longitude: place.lon,
        }));

        setLocationOptions(options);
      } catch (error) {
        console.error('Error fetching location:', error);
      }
    }, 500);
  };

  useEffect(() => {
    return () => {
      if (timeoutRef.current) {
        clearTimeout(timeoutRef.current);
      }
    };
  }, []);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [event.target.name]: event.target.value });
    console.log(formData);
  };

  const handleLocationChange = (selectedOption: any) => {
    console.log('Locating is changing to:', selectedOption);
    setLatitude(selectedOption.latitude);
    setLongitude(selectedOption.longitude);
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();


    if (!latitude || !longitude) {
      alert('Please select a location');
      return;
    }

    try { 
      fetchTimeZone(latitude,longitude);

    if(error){
      alert(`Failed to load timezone: ${error}`)
      return;
    }


    onSearch({
      latitude,
      longitude,
      startDateTime: `${formData.fromDate}T${formData.fromTime}`,
      endDateTime: `${formData.untilDate}T${formData.untilTime}`,
      timezone: timezone
  });
  }catch(error){
    console.error('Error submitting form: ',error);
  }
  };


  return (
    <div className="absolute top-[20%] left-1/2 transform -translate-x-1/2 bg-white/80 shadow-sm rounded-full p-2   backdrop-blur-md">
      <form onSubmit={handleSubmit} className="flex items-center space-x-3">
        
        <div className="flex-1">
          <Select
            options={locationOptions}
            onInputChange={handleLocationSearch}
            onChange={handleLocationChange}
            placeholder="Where to?"
            className="w-full p-2 text-sm border-none bg-transparent focus:outline-none placeholder-gray-500"
            />
        </div>
  
        <input
          type="date"
          name="fromDate"
          value={formData.fromDate}
          onChange={handleInputChange}
          className="p-2 text-sm border border-gray-300 rounded-full w-[120px]"
        />
  
        <input
          type="time"
          name="fromTime"
          value={formData.fromTime}
          onChange={handleInputChange}
          className="p-2 text-sm border border-gray-300 rounded-full w-[100px]"
        />
        <input
          type="date"
          name="untilDate"
          value={formData.untilDate}
          onChange={handleInputChange}
          className="p-2 text-sm border border-gray-300 rounded-full w-[120px]"
        />
  
        <input
          type="time"
          name="untilTime"
          value={formData.untilTime}
          onChange={handleInputChange}
          className="p-2 text-sm border border-gray-300 rounded-full w-[100px]"
        />
  
        <button
          type="submit"
          className="bg-indigo-500 text-white p-3 rounded-full hover:bg-indigo-600 flex items-center justify-center w-[45px] h-[45px]"
        >
          <FaSearch />
        </button>
  
      </form>
    </div>
  );
}

export default HeroListingFinder