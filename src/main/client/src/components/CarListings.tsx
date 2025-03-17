// we want to keep app component clean
// single listing component will be one block
import React from 'react'
import CarListing from './CarListing'
import {useState,useEffect} from 'react';
import Spinner from './Spinner'; 
const CarListings = ({isHome = false}) => {
  // add state for jobs
  const [cars,setCars] = useState([]);
  const [loading,setLoading] = useState(true);

  useEffect( () => {

    const fetchCars = async () => {
      // need logic to fetch relevant cars
      // capture form data from search and use it to query api 
      const apiUrl = '/api/cars'
      try{
        const res = await fetch(apiUrl);
        const data = await res.json();
        setCars(data); // update our array of cars with car json objects

      }
      catch(error){
        console.log('error fetching cars from api',error);
      } finally {
        setLoading(false); 
      }
    }
    fetchCars();
  }, []);

  return (
    <section>
      { loading 
      ? (<Spinner loading = {loading}>Loading....</Spinner>)
      :
      (<div className = "grid grid-cols-1 md:grid-cols-3 gap-6">
        {cars.map((car) =>
          <CarListing key={car.id} car={car}/>
        ))}
      </div>)}

    </section>
    
  )
}

export default CarListings