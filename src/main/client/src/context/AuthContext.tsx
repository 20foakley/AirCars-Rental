import { createContext, useContext, useEffect, useState, ReactNode } from "react";
import {refreshUser,registerUser,loginUser,logoutUser} from '../services/authService';
import {LoginCredentials,RegisterCredentials} from '../types/auth';
import {User} from '../types/user';




interface AuthContextType {
  user: User | null;
  loading: boolean;
  login: (LoginCredentials: LoginCredentials) => Promise<void>;
  logout: () => Promise<void>;
  register: (RegisterCredentials: RegisterCredentials) => Promise<void>;
  refreshUser: () => Promise<void>
}

const AuthContext = createContext<AuthContextType | null>(null); 

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  // check user on page load, loading state avoids flickering
  const [loading, setLoading] = useState(true);


  useEffect(() => {
    refreshUser().then(data => {setUser(data);
        console.log('User validated successfuly! ',data.username);       
  })
      .catch((err) => {
        if (err.response?.status === 403) {
          console.log("User not logged in");
        }
        else if (err.response?.status === 400) {
          console.log("Bad Request");
          
        }

        setUser(null)
})
      .finally(() => setLoading(false));
  }, []);



const login = async (loginCredentials: LoginCredentials) => {
  // backend directly sets access and refreshtoken
  // only worry about refresh token (we get it from /auth/me)
  try { 
    const user = await loginUser(loginCredentials);
    console.log('user: ',user.username)
    setUser(user);
  }
  catch (error:any) {
    const message = error?.response?.data?.message || "Login failed";
    throw message;
  }
};

const logout = async () => {
  try {
    await logoutUser();
    setUser(null);
  }
  catch(error){
    console.error(error);
  }
};

const register = async(registerCredentials : RegisterCredentials) => {
  try { 
    await registerUser(registerCredentials);
    const user = await loginUser(registerCredentials);
    setUser(user);
  }
  catch (error: any){
    const message = error?.response?.data?.message || error?.response?.data || 'Registration failed';
    throw message;
  }
  
}

  return (
    <AuthContext.Provider value={{ user, login, logout, refreshUser, register, loading }}>
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
