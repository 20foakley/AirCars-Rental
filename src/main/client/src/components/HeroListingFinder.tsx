import React, { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Select from 'react-select';
import useUserLocation from '../hooks/useUserLocation';
import useReverseGeocode from '../hooks/useReverseGeocode';
import { FaSearch } from 'react-icons/fa';

const NOMINATIM_BASE_URL = "https://nominatim.openstreetmap.org/search?format=json&addressdetails=1&limit=5&q=";

interface HeroListingFinderProps {
  onSearch: ({latitude, longitude, startDateTime, endDateTime, timezone}: {
    latitude: string;
    longitude: string;
    startDateTime: string;
    endDateTime: string;
    timezone?: string;
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
  const { location } = useUserLocation();

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
      timezone
  });
  }catch(error){
    console.error('Error submitting form: ',error);
  }
  };


  return (
    <>
      <div className="absolute top-[20%] left-1/2 transform -translate-x-1/2 bg-white shadow-lg rounded-lg p-6 w-[90%] max-w-6xl">
        <form onSubmit={handleSubmit} className="flex items-center justify-between space-x-6">
          
          <div className="flex flex-col w-1/4">
            <label className="text-lg font-semibold">Where</label>
            <Select
              options={locationOptions}
              onInputChange={handleLocationSearch}
              onChange={handleLocationChange}
              placeholder="Enter an address"
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          </div>
  
          <div className="flex flex-col w-1/6">
            <label className="text-lg font-semibold">From</label>
            <input
              type="date"
              name="fromDate"
              value={formData.fromDate}
              onChange={handleInputChange}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          </div>

          <div className="flex flex-col w-1/6">
            <label className="text-lg font-semibold">Time</label>
            <input
              type="time"
              name="fromTime"
              value={formData.fromTime}
              onChange={handleInputChange}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          </div>

          <div className="flex flex-col w-1/6">
            <label className="text-lg font-semibold">Until</label>
            <input
              type="date"
              name="untilDate"
              value={formData.untilDate}
              onChange={handleInputChange}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          </div>

          <div className="flex flex-col w-1/6">
            <label className="text-lg font-semibold">Time</label>
            <input
              type="time"
              name="untilTime"
              value={formData.untilTime}
              onChange={handleInputChange}
              className="w-full p-2 border border-gray-300 rounded-md"
            />
          </div>
  
          <div className="flex items-center">
            <button
              type="submit"
              className="bg-indigo-500 text-white py-3 px-6 rounded-md hover:bg-indigo-600 w-full sm:w-auto"
            >
              <FaSearch className="mr-2"/>
            </button>
          </div>
  
        </form>
      </div>
    </>
  );
}

export default HeroListingFinder