// have to import this in App.jsx
// then create a parent route to rest of our routes
// 

import React from 'react'
import {Outlet} from 'react-router-dom'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import Navbar from '../components/Navbar'


const MainLayout = () => {
  return (

    <>
        <Navbar />
        <Outlet/>
        <ToastContainer/>
    </>
  )
}

export default MainLayout