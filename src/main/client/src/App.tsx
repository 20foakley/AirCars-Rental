import React from 'react';
import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
  RouterProvider
} from 'react-router-dom';

import HomePage from './pages/HomePage.tsx';
import MainLayout from './layouts/MainLayout';
import CarsPage from './pages/CarsPage.tsx';
import NotFoundPage from './pages/NotFoundPage.tsx';
import ProfilePage from './pages/ProfilePage.tsx'
import { AuthProvider } from './context/AuthContext.tsx'
import PrivateRoute from './routes/PrivateRoute.tsx';

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<MainLayout />}>
      <Route index element={<HomePage />} />
      <Route path="/listings" element={<CarsPage />} />
      <Route
        path="/profile"
        element={
          <PrivateRoute>
            <ProfilePage />
          </PrivateRoute>
        }
      />

      <Route path="*" element={<NotFoundPage />} />
    </Route>
  )
);

const App = () => {
  return (
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  );
};

export default App;
