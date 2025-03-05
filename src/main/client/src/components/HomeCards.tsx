import React from 'react';
import Card from './Card';
import { Link } from 'react-router-dom';
import Listings_Card_Image1 from '../assets/images/touge_spirit___rx7_by_jhaqastar_d647lgo-fullview.jpg'
import Listings_Card_Image2 from '../assets/images/listings-card.jpg'

const HomeCards = () => {
  return (
    <section className="py-4">
      <div className=" bg-white text-lg font-bold p-6 rounded-lg shadow-md justify-center items-center align-middle">
        Find your perfect fit anywhere, anytime.
        <div font-normal>Spice up your plans.</div>
      </div>



      <div className="flex bg-gray-100 p-6 rounded-lg shadow-md items-center justify-between">
        
        <div className="w-1/2 pr-4">
          <picture>
            <img
              src={Listings_Card_Image1}
              alt="Car browsing"
              className="w-full h-auto rounded-lg"
            />
          </picture>
        </div>

        <div className="w-1/2 pl-4">
          <Card bg="bg-indigo-100">
            <h2 className="text-2xl font-bold">Dreams come true here.</h2>
            <p className="mt-2 mb-4">
              No matter the occasion, find a ride that'll take you to cloud nine.
            </p>
            <Link
              to="/listings"
              className="inline-block bg-black text-white rounded-lg px-4 py-2 hover:bg-gray-700"
            >
              Explore Cars
            </Link>
          </Card>
        </div>

      </div>
    </section>
  );
};

export default HomeCards;
