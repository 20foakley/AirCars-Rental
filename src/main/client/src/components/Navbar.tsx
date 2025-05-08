import React from 'react'
import logo from '../assets/images/car_icon_126245.webp'
import {NavLink} from 'react-router-dom'
import { Menu } from "lucide-react";
import { useAuth } from "./context/AuthContext";

import HamburgerModal from "./modals/HamburgerModal.tsx"


const Navbar = () => {

  // highlighting navbar icons
  const linkClass =  ({isActive} : {isActive:any}) =>  isActive ? 
  'bg-yellow text-black hover:bg-gray-900 hover:text-white rounded px-3 py-2' 
  : 'text-black hover:bg-gray-900 hover:text-white rounded px-3 py-2'
  // is hamburger open or closed?
  const [isOpen, setIsOpen] = React.useState(false);
  const {user,logout} = useAuth(); 
  return (
    <>
    <nav className="bg-white">
      <div className="mx-auto px-2 sm:px-6 lg:px-8">
        <div className="flex h-20 items-center justify-between">
          <div
            className="flex flex-1 items-center justify-center"
          >
            {/* <!-- Logo --> */}
            <NavLink className="flex flex-shrink-0 items-center mr-4" to="/">
              <img
                className="h-12 w-auto"
                src={logo}
                alt="Car Rentals"
              />
              <span className="text-black text-2xl font-bold ml-2"
                >Car Rentals</span
              >
            </NavLink>
            <div className="ml-auto">
              <div className="flex space-x-2 items-center">

                {/* need to add login/signup vs logout with state */}

                <button onClick = {() => setIsOpen(!isOpen)} className = "">
                  <Menu className = "z-10 w-6 h-6 text-black"/>
                </button>
                {isOpen && (
                  <HamburgerModal/>

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