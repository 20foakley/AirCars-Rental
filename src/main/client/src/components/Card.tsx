import React from 'react'

const Card = ({children,bg='bg-gray-100'}) => { // we get bg (color) from Card prop
  return <div className = {`${bg} p-6 rounded-lg shadow-md`}>{children}</div>
};

export default Card;