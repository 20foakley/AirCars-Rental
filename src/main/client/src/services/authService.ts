import {LoginCredentials,RegisterCredentials} from '../types/auth'
import apiClient from './apiClient';

// can implement useCallBack 
export const loginUser = async (loginCredentials: LoginCredentials) => {
    console.log("loginUser credentials: ", loginCredentials)
    await apiClient.post('/auth/login',loginCredentials)
    await new Promise(res => setTimeout(res,100));
    return refreshUser();
}; 

export const refreshUser = async  () => { // browser takes magic cookie from its jar, shows it to backend, then gets back user info!
    const res = await apiClient.get('/auth/me')
    console.log("refreshUser data" + res.data);
    return res.data; 
};

export const logoutUser = async () => {
    const res = await apiClient.post('/auth/logout')
    return res.data;
};

export const registerUser = async (registerCredentials: RegisterCredentials) => {
    const res = await apiClient.post('/auth/register',
        registerCredentials
    ,
    { headers: { 'Content-Type': 'application/json' }}

);
    return res.data;
};