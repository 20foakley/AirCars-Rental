import React, {useState} from 'react';
import axios from 'axios';
import {useAuth} from '../../context/AuthContext';


const SignupModal = ({ onClose }: { onClose: () => void }) => {
  const [username,setUsername] = useState('');
  const [email,setEmail] = useState('');
  const [password,setPassword] = useState('');
  const [errorMsg,setErrorMsg] = useState('');
  const [successMsg,setSuccessMsg] = useState('');
  const {register, refreshUser} = useAuth();



  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrorMsg('');
    setSuccessMsg('');

    if(username.length < 4){
      setErrorMsg('Username must be at least four characters long.');
      return;
    }

    if(password.length < 8){
      setErrorMsg('Password must be at least eight characters long.');
      return;
    }

    try {
      // register user
      const registerResponse = await register({username,password,email})
      console.log('Register returned data:', registerResponse);
      console.log('Registered user:', refreshUser());
      setSuccessMsg('Successfully registered! Logging in...');
      onClose();

     setTimeout(() => {
        onClose();
      }, 1000);

    } catch (error: any) { // need a better way to extract error messages
      console.error('Registration or login failed:', error);
      if (typeof error == 'string') {
        setErrorMsg(error);
      }
      if(error.response && error.response.data){
        setErrorMsg(error.response.data) // backend caught existing username/email
      }
      else{ 
        setErrorMsg('Sorry, something went wrong on our end. Please try again.');
      }
    }

  }


    return (
    <div className="fixed inset-0 bg-black bg-opacity-50 z-50 flex justify-center items-center">
      <div className="bg-white rounded-lg p-6 shadow-xl w-96 relative">
        <button
          onClick={onClose}
          className="absolute top-2 right-2 text-gray-500 hover:text-black"
        >
          ✕
        </button>
        <h2 className="text-xl font-bold mb-4">Sign Up</h2>

        {errorMsg && <p className="text-red-500 text-sm mb-2">{errorMsg}</p>}
        {successMsg && <p className="text-green-600 text-sm mb-2">{successMsg}</p>}

        <form onSubmit={handleSubmit} className="flex flex-col space-y-4">
          <input
            type="text"
            placeholder="Username"
            value={username}
            required
            onChange={(e) => setUsername(e.target.value)}
            className="border border-gray-300 rounded px-3 py-2"
            autoComplete="username"
          />
          <input
            type="email"
            placeholder="Email"
            value={email}
            required
            onChange={(e) => setEmail(e.target.value)}
            className="border border-gray-300 rounded px-3 py-2"
            autoComplete="email"
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            required
            onChange={(e) => setPassword(e.target.value)}
            className="border border-gray-300 rounded px-3 py-2"
            autoComplete='new-password'
          />
          <button
            type="submit"
            className="bg-blue-600 text-white rounded px-4 py-2 hover:bg-blue-700"
          >
            Sign Up
          </button>
        </form>
      </div>
    </div>
  );
};

export default SignupModal;
