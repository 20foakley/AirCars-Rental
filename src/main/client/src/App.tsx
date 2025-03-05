import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
  RouterProvider
} 
from 'react-router-dom'
import React from 'react'
import HomePage from './pages/HomePage.tsx'
import MainLayout from './layouts/MainLayout'
import CarsPage from './pages/CarsPage.tsx'


const App = () => {

  // App is what runs and creates your routes
  // : symbolizes that whatever comes after it is dynamic, 
  const router = createBrowserRouter(
    createRoutesFromElements(
    <Route path='/' element ={<MainLayout />}>
      <Route index element ={<HomePage /> }/>
      <Route path = '/listings' element ={<CarsPage/>}/>
    </Route>
)
)
  return <RouterProvider router = {router} />;

}

export default App