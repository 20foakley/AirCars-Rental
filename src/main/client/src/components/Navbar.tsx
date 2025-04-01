import React from 'react'
import logo from '../assets/images/car_icon_126245.webp'
import {NavLink} from 'react-router-dom'
import { Menu } from "lucide-react";


const Navbar = () => {

  // highlighting navbar icons
  const linkClass =  ({isActive} : {isActive:any}) =>  isActive ? 
  'bg-yellow text-black hover:bg-gray-900 hover:text-white rounded px-3 py-2' 
  : 'text-black hover:bg-gray-900 hover:text-white rounded px-3 py-2'
  // is hamburger open or closed?
  const [isOpen, setIsOpen] = React.useState(false);

  return (
    <>
    <nav className="bg-white">
      <div className="mx-auto px-2 sm:px-6 lg:px-8">
        <div className="flex h-20 items-center justify-between">
          <div
            className="flex flex-1 items-center justify-center md:items-stretch md:justify-start"
          >
            {/* <!-- Logo --> */}
            <NavLink className="flex flex-shrink-0 items-center mr-4" to="/">
              <img
                className="h-12 w-auto"
                src={logo}
                alt="Car Rentals"
              />
              <span className="hidden md:block text-black text-2xl font-bold ml-2"
                >Car Rentals</span
              >
            </NavLink>
            <div className="md:ml-auto">
              <div className="flex space-x-2">
                <NavLink
                  to="/"
                  className={linkClass}
                  >Home</NavLink
                >
                <NavLink
                  to="/listings"
                  className={linkClass}
                  >Cars</NavLink
                >
                <NavLink
                  to="/profile"
                  className={linkClass}
                  >Profile</NavLink
                >

                {/* need to add login/signup vs logout with state */}

                <button onClick = {() => setIsOpen(!isOpen)} className = "">
                  <Menu className = " z-10 w-6 h-6 text-black"/>
                </button>
                {isOpen && (
                  <div className = "absolute right-0 top-12 bg-gray-700 p-4 rounded">
                    <NavLink
                      to="#"
                      className = {linkClass}
                    >Link 1</NavLink>
                                        <NavLink
                      to="#"
                      className = {linkClass}
                    >Link 2</NavLink>
                  </div>



                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>

    
    </>
  )
}

export default Navbar