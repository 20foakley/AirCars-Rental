

export interface BaseAuthCredentials {
    username:string;
    password:string;
}

export interface LoginCredentials extends BaseAuthCredentials {}

export interface RegisterCredentials extends BaseAuthCredentials {
    email: string;
}

