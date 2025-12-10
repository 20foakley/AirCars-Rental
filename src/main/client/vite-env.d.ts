/// <reference types="vite/client" />
/// <reference types="vite/types/importMeta.d.ts" />


interface ImportMetaEnv {
    readonly VITE_GEOAPIFY_REVERSE_GEOCODING_KEY: string;
    readonly VITE_GEOAPIFY_REVERSE_GEOCODING_URL: string;
    readonly VPIC_URL : string;
  }
  
  interface ImportMeta {
    readonly env: ImportMetaEnv;
  }

