import { createContext, useContext, useEffect, useState, ReactNode } from "react";
import axios from 'axios';
import React from 'react';
import {fetchCurrentUser,loginUser,logoutUser} from '../services/authService';
import {Credentials} from '../types/auth';


// now uses HTTP-only cookies as opposed to storing cookies in localStorage (safer!)
// cookies are stored automatically by browser in its cookie jar, and used to show to back-end (bouncer) like a "VIP" pass or keycard


interface User {
  username: string;
}

// store user object

interface AuthContextType {
  user: User | null;
  loading: boolean;
  login: (credentials: Credentials) => Promise<void>;
  logout: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | null>(null); // we have an OR there bc till a value is passed via a Provider, AuthContext is null

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  // check user on page load, loading state avoids flickering
  const [loading, setLoading] = useState(true);


  useEffect(() => {
    fetchCurrentUser().then(data => {setUser(data);
        console.log('User validated successfuly! ',data.username);       
  })
      .catch((err) => {
        if (err.response?.status === 401) {
          console.log("User does not exist")
        } else if (err.response?.status === 403) {
          console.log("User not logged in, or internal server error")
        }
        setUser(null)
})
      .finally(() => setLoading(false));
  }, []);

const login = async (credentials: Credentials) => {
  // backend directly sets access and refreshtoken
  // only worry about refresh token (we get it from /auth/me)
  const user = await loginUser(credentials);
  console.log('user: ',user.username)
  setUser(user);
};

  const logout = async () => {
    await logoutUser();
    setUser(null);
  };

  // provide all functionality to app via context
  return (
    <AuthContext.Provider value={{ user, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};
