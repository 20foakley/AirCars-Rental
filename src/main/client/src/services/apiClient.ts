import axios from "axios"

// If we get a 401 or 403 due to expired access token, then intercept (call /api/auth/refresh and retry request)

const apiClient = axios.create(
    {
        baseURL: '/api',
        withCredentials: true,
    }
);