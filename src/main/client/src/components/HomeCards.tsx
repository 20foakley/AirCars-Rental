import React from 'react';
import { Link } from 'react-router-dom';
import Card from './Card'
import Listings_Card_Image1 from '../assets/images/autosnap-cars.png'
import Listings_Card_Image2 from '../assets/images/autosnap-cars2.png'

const HomeCards = () => {
  return (
    <section className="py-8">

      <Card 
        image={Listings_Card_Image1}
        altText="Variety of Cars"
        heading="Dreams come true here."
        description="No matter the occasion, find a ride that'll take you to cloud nine."
        buttonText="Explore Cars"
        buttonLink="/listings"
        imageOnLeft={true}
      />

      
      <Card 
        image={Listings_Card_Image2}
        altText="Variety of Cars"
        heading="Be your own boss."
        description="List your car and start building an empire."
        buttonText="List a Car"
        buttonLink="/listings"
        imageOnLeft={false}
      />

    </section>
  );
};

export default HomeCards;
