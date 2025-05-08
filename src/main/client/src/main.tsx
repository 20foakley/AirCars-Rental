import React from 'react'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App'
import { AuthProvider } from './components/context/AuthContext'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthProvider>
      {/* wrapping app in AuthProvider shares state (user,login,logout with entire app) */}
      <App />
    </AuthProvider>
  </StrictMode>,
)
