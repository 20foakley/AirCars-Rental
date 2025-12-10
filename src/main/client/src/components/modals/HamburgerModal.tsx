import React, { useState } from 'react';
import {NavLink} from 'react-router-dom'
import { useAuth } from "../../context/AuthContext";
import LoginModal from './LoginModal';
import SignupModal from './SignupModal';
import VinModal from './VinModal';



const HamburgerModal = ({ onClose }: { onClose: () => void }) => {
  // need to use AuthContext to determine whether to show sign up/login or just logout
  const { user, logout } = useAuth();
  //console.log("hamburger modal user:", user);
  const [showLoginModal, setShowLoginModal] = useState(false);
  const [showSignupModal, setShowSignupModal] = useState(false);
  const [showVinModal, setShowVinModal] = useState(false);
  const linkClass =  ({isActive} : {isActive:boolean}) =>  isActive ? 
  'bg-yellow text-black hover:bg-gray-600 hover:text-white rounded px-3 py-2' 
  : 'text-black hover:bg-gray-600 hover:text-white rounded px-3 py-2'
  return (
    <>
      <div className="fixed inset-0 z-40" onClick={onClose} ></div>
      <div className="absolute right-0 top-20 w-36 bg-white shadow-lg rounded-2xl p-4 z-50 flex flex-col items-center space-y-2 border border-gray-200">
        {user ? (
          <>
            <NavLink to="/profile" className={linkClass} onClick={onClose}>
              Profile
            </NavLink>
            <button onClick={ () => {logout(); onClose();}}           
            className={linkClass({ isActive: false })}>
              Log Out
            </button>
            <button onClick={() => {setShowVinModal(true); onClose;}} className={linkClass({ isActive: false })}>
              Add a Car
            </button> 
          </>
        ) : (
          <>
            <button onClick={() => {setShowLoginModal(true); onClose;}} className={linkClass({ isActive: false })}>
              Log In
            </button>
            <button onClick={() => {setShowSignupModal(true); onClose;}} className={linkClass({ isActive: false })}>
              Sign Up
            </button>
          </>
          )}
        </div>
          {showLoginModal && <LoginModal onClose={() => setShowLoginModal(false)} />}
          {showSignupModal && <SignupModal onClose={() => setShowSignupModal(false)} />}

        </>




  );
};
export default HamburgerModal