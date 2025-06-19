import axios from 'axios'
import {Credentials} from '../types/auth'


// can implement useCallBack 
export const loginUser = async (credentials: Credentials) => {
    await axios.post('/api/auth/login', credentials, {withCredentials: true});
    await new Promise(res => setTimeout(res,100));
    return fetchCurrentUser();
}; 

export const fetchCurrentUser = async  () => { // browser takes magic cookie from its jar, shows it to backend, then gets back user info!
    const res = await axios.get('/api/auth/me', {withCredentials:true});
    console.log("fetchCurrentUser data" + res.data);
    return res.data; 
};

export const logoutUser = async () => {
    await axios.post('/api/auth/logout', {}, {withCredentials: true});
};

export const registerUser = async ({username,password,email}) => {
    const res = await axios.post('/api/auth/register',{
        username,
        password,
        email
    });
    return res.data;


}