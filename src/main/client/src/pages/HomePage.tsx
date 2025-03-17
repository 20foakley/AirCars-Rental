import React,{useState} from 'react'
import {useNavigate} from 'react-router-dom';
import Hero from '../components/Hero';
import HomeCards from '../components/HomeCards'
import CarListings from '../components/CarListings';
import ViewAllCars from '../components/ViewAllCars';
import HeroListingFinder from '../components/HeroListingFinder';

// don't need navbar here - it's gonan show on all pages
//but hero component we only want here


const HomePage = () => {

  // grab user's search parameters and route user to CarsPage w/ fetched relevant listings 

  const navigate = useNavigate();

  const handleSubmit = async (searchParams: {
    latitude:string,
    longitude:string, 
    startDateTime:string;
    endDateTime: string; 
  }) => {
    try {
      // send query to backend where we can find active listings - booked listings active at that date/time
      // backend has to be able to find long_lats close to user's specified location
      const response = await fetch(`/api/listings?latitude=${searchParams.latitude}&longitude=${searchParams.longitude}&startDateTime=${searchParams.startDateTime}&endDateTime=${searchParams.endDateTime}`);
      
      if(!response.ok) {
        throw new Error('HTTP error - status ${response.status}');
      }
           
      const data = await response.json();

      navigate('/cars', {state: {listings: data}});

    } catch(error) {
      console.error('Failed to fetch listings:',error);
    }
  }

  return (
    <>
      <Hero/>
      <HeroListingFinder onSearch={handleSubmit}/>
      <HomeCards/>
    </>
  );
};

export default HomePage