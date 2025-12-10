import axios from 'axios';
import { AxiosRequestConfig } from 'axios';


// this is a central interface for sending requests, 
// and for intercepting expired access tokens


interface CustomAxiosRequestConfig extends AxiosRequestConfig {
  _retry?: boolean;
}


const apiClient = axios.create({
  baseURL: '/api',
  withCredentials: true, 
});

// these two variables handle condition where you have concurrent refresh requests
// when isRefreshing is set true, requests are processed in queue

let isRefreshing = false;
let failedQueue: {
    resolve: (value?: unknown) => void;
    reject: (reason?: any) => void;
}[] = [];

const discriminateQueueMembers = (error: any, user:any=null) => {
    failedQueue.forEach(promise => {
        if(error){
            promise.reject(error);
        } else {
            promise.resolve(user);
        }
});
    failedQueue = [];
}

apiClient.interceptors.response.use(
    response => response, // successful responses don't need any attention
    async error => {
        const originalRequest = error.config; 
    

    if((error.response?.status === 401 || error.response?.status === 403) 
        && !originalRequest._retry) {
            if(isRefreshing){
                return new Promise((resolve,reject) => {
                    failedQueue.push({resolve,reject});
                })
                    .then(() => apiClient(originalRequest))
                    .catch(err => Promise.reject(err));
            }

            const originalRequest = error.config as CustomAxiosRequestConfig
            originalRequest._retry = true; // needed type specification for use in TS
            isRefreshing=true;

            try { 
                await apiClient.post('/auth/refresh',{}, {withCredentials:true});
                discriminateQueueMembers(null);
            } 
            catch(refreshError) {
                discriminateQueueMembers(refreshError,null)
                return Promise.reject(refreshError)
            }
            finally {
                isRefreshing=false;
            }


        }
        return Promise.reject(error);
    }
);

export default apiClient;

// refresh the token, retry first request, then retry other requests