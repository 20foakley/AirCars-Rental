import React from 'react'
import Hero from '../components/Hero';
import HomeCards from '../components/HomeCards'
import CarListings from '../components/CarListings';
import ViewAllCars from '../components/ViewAllCars';
import HeroListingFinder from '../components/HeroListingFinder';

// don't need navbar here - it's gonan show on all pages
//but hero component we only want here


const HomePage = () => {
  return (
    <>
      <Hero/>
      <HeroListingFinder/>
      <HomeCards/>
      <CarListings isHome={true}/>
      <ViewAllCars/>   
    </>
  )
}

export default HomePage