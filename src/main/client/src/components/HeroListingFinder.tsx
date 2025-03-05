import React,{ useState } from 'react'
import Select from "react-select";


// This is a simple form box for users to submit and find listings at a general location
// Uses OpenStreetMap Nominatim API for address autocomplete
const NOMINATIM_BASE_URL = "https://nominatim.openstreetmap.org/search?format=json&q=";

const HeroListingFinder = () => {

  const [formData, setFormData] = useState({
    where:'',
    from:'',
    until:''
  });

  const [locationOptions, setLocationOptions] = useState([]);

  const handleLocationSearch = async (inputValue: string) => {
    if (!inputValue) return;
    try {
      // fetch address match dynaimcally
      const response = await fetch(`$NOMINATIM_BASE_URL}${inputValue}`);
      const data = await response.json();

      const options = data.map((place:any) => ({
        label: place.display_name,
        value: place.display_name,
        lat: place.lat,
        lon: place.lon,
      }));

      setLocationOptions(options);
    } catch (error) {
      console.log("error fetching location ",error);
    };
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [event.target.name]: event.target.value })
  };

  const handleLocationChange = (selectedOption: any) => {
    setFormData({ ...formData, where: selectedOption.value });
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => { // React.formevent helps keep track of our state, and ensures type safety.
    event.preventDefault();
    //console.log("Form Data: ",formData);
  };
  return (
        <>
          <div className = 'absolute top-[20%] left-1/2 transform -translate-x-1/2 bg-white shadow-lg rounded-lg p-4 w-[80%] max-w-md'>
            <form onSubmit ={handleSubmit} className = "flex flex-wrap space x-4">
              <label className = "text-sm font-medium">Where</label>
              <Select
                options={locationOptions}
                onInputChange={handleLocationSearch}
                onChange={handleLocationChange}
                placeholder="Enter an address, city, or location"
                className="w-full sflex-1 p-2 border border-gray-300 rounded-md"
              />
        <label className="text-sm font-medium">From</label>
        <input type="date" name="from" value={formData.from} onChange={handleInputChange} className="w-full flex-1 p-2 border border-gray-300 rounded-md" />

        <label className="text-sm font-medium">Until</label>
        <input type="date" name="until" value={formData.until} onChange={handleInputChange} className="w-full flex-1 p-2 border border-gray-300 rounded-md" />

        <button type="submit" className="bg-indigo-500 text-white p-2 rounded-md hover:bg-indigo-600"></button>Search <button/>

            </form>
          </div>
        </>
  );
}

export default HeroListingFinder