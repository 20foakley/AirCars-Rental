import React, {useState} from 'react';
import axios from 'axios';


const LoginModal = ({ onClose }: { onClose: () => void }) => { // onClose defined by HamburgerModal
  const [username,setUsername] = useState('');
  const [password,setPassword] = useState('');
  const [remember,setRemember] = useState(false);
  const [errorMsg,setErrorMsg] = useState('');
  const [successMsg,setSuccessMsg] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();Sho
    console.log('login submit ', {username,password,remember})

    try { 
      const loginResponse = await axios.post('http://localhost:8080/auth/login', { username, password }, { withCredentials: true });
      const { token } = loginResponse.data;
      console.log('Logged in, JWT:', token);

      localStorage.setItem('jwt', token); // token persists. can change this


      setTimeout(() => {
        onClose();
      }, 1000);
    }
    catch (error: any) {
    console.error('Registration or login failed:', error);
    setErrorMsg('Sorry, something went wrong. Please try again.');
    }
  }
   
  
  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 z-50 flex justify-center items-center">
      <div className="bg-white rounded-lg p-6 shadow-xl w-96 relative">
        <button onClick={onClose} className="absolute top-2 right-2 text-gray-500 hover:text-black">✕</button>
        <h2 className="text-xl font-bold mb-4">Login</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label>
              <input
                type = "username"
                value= {username}
                onChange={(e)=> setUsername(e.target.value)}
                required
                className=""
                autoComplete = "username"
              />
            </label>
          </div>
          <div>
            <label>
              <input
                type = "password"
                value= {password}
                onChange={(e)=> setPassword(e.target.value)}
                required
                className=""
              />
            </label>
          </div>
          <div>
            <label>
              <input
                type = "checkbox"
                checked= {remember}
                onChange={(e)=> setRemember(!remember)}
                className=""
                autoComplete='current-password'
              />
              <>Remember Me</>
            </label>
            <button
              type = "button"
              className=""
              onClick= {() => alert('redirecting user to reset password modal')}
            >
              Forgot Password?
            </button>
          </div>
          <button
            type="submit"
            className=""
          >
            Login
          </button>
        </form>
        
        
        
        
        
        
        
        
        
      </div>
    </div>
  );
};

export default LoginModal;
