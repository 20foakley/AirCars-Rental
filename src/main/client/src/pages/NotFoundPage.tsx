import React from 'react'
import {Link} from 'react-router-dom'
import {FaRegSurprise } from 'react-icons/fa'
const NotFoundPage = () => {
  return (

        <section className="text-center flex flex-col justify-center items-center h-96">
        <FaRegSurprise  className = 'fa-4x text-6xl mb-4'/>
          <h1 className="text-6xl font-bold mb-4">Whoops!</h1>
          <p className="text-xl mb-5">Looks like this page has been moved or never existed. Need a lift back home?</p>
          <Link
            to="/"
            className="text-white bg-indigo-700 hover:bg-indigo-900 rounded-md px-3 py-2 mt-4"
            >Go Back</Link>
        </section>
  )
}

export default NotFoundPage