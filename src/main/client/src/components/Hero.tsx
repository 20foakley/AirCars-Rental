import React from "react";
import HeroListingFinder from "./HeroListingFinder";
import Hero_Image from '../assets/images/hero-car.jpg'


const Hero = () => {
  return (
    <section className="bg-white">
      <div className="relative w-full h-[60vh] mt-6"> 
        <HeroListingFinder />
        <picture className="absolute inset-0 w-full h-full">
          {/* fallback image */}
          <img
            alt="Man stepping out of Ford Bronco with snowy background"
            src={Hero_Image}
            className="w-full h-full object-cover scale-110"
          />
        </picture>       
      </div>
    </section>
  );
};

export default Hero;
