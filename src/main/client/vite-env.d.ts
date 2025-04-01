/// <reference types="vite/client" />

interface ImportMetaEnv {
    readonly VITE_GEOAPIFY_REVERSE_GEOCODING_KEY: string;
    readonly VITE_GEOAPIFY_REVERSE_GEOCODING_URL: string;
  }
  
  interface ImportMeta {
    readonly env: ImportMetaEnv;
  }

