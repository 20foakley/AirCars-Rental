import React from 'react'
// Take in some children
// wrap child (h1/html) in a card
// also want card styling (for developers for employers
const Card = ({children,bg='bg-gray-100'}) => { // we get bg (color) from Card prop
  return <div className = {`${bg} p-6 rounded-lg shadow-md`}>{children}</div>
};

export default Card;