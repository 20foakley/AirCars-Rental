import React from 'react'
import { Link } from 'react-router-dom';

interface CardProps {
  image: string;
  altText: string;
  heading: string;
  description: string;
  buttonText: string;
  buttonLink: string;
  imageOnLeft?: boolean;
}

const Card = ( cardProps:CardProps ) => { 

  const {
    image,
    altText,
    heading,
    description,
    buttonText,
    buttonLink,
    imageOnLeft = true,
  } = cardProps;

  return (
    // fun template literal - need {} around everything to write JS inside of JSX
      <div className={`flex flex-col ${imageOnLeft ? 'md:flex-row' : 'md:flex-row-reverse'} bg-gray-100 p-6 rounded-lg shadow-md items-center justify-between`}>
              
        <div className="w-1/2 pr-4">
          <picture>
            <img
              src={image}
              alt={altText}
              className="wmax-w-full max-h-64 object-cover rounded-l"
            />
          </picture>
        </div>

        <div className="w-1/2 pl-4">
            <h2 className="text-2xl font-bold">{heading}</h2>
            <p className="mt-2 mb-4">
              {description}
            </p>
            <Link
              to={buttonLink}
              className="inline-block bg-black text-white rounded-lg px-4 py-2 hover:bg-gray-700"
            >
              {buttonText}
            </Link>
        </div>

        

      </div>)
        

};

export default Card;