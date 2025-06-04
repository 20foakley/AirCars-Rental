import axios from 'axios'
import {Credentials} from '../types/auth'


export const loginUser = async (credentials: Credentials) => {
    await axios.post('/auth/login', credentials, {withCredentials: true});
    await new Promise(res => setTimeout(res,100));
    return fetchCurrentUser();
}; 

export const fetchCurrentUser = async  () => { // browser takes magic cookie from its jar, shows it to backend, then gets back user info!
    const res = await axios.get('/auth/me', {withCredentials:true});
    return res.data; 
};

export const logoutUser = () => {
    axios.post('/auth/logout', {}, {withCredentials: true});
};