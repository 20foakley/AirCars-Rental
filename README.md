**AirCars-Rental (title is work in progess! :P)
**
A full-stack app using Spring Boot, React, and PostgreSQL. The purpose this app is to allow users to browse cars in a given area that are available for rental, and to list their own vehicles for rent.

Used Vite as a front-end dev tool, and Tailwind CSS to make designing cleaner/more streamlined. For backend I am making use of dependencies
Spring Security and JSON Web Tokens to name a few.
I'm also making use of the GeoApify's APIs for generating global coordinates based on text addresses, and providing autocomplete for addresses.

**HOW TO BUILD
**
You'll need:
1. Node.js (v18 or later)
2. npm or yarn
3. Java JDK 17+
4. Maven (if there are any issues, I am using Spring 3.2.5).
5. PostgreSQL (or your configured database and the proper Spring dependency)

I've included my application.properties for the Spring Boot backend (it doesn't contain anything bad!) but not my .env for front-end that contains:

VITE_GEOAPIFY_REVERSE_GEOCODING_KEY= {insert-key-here}
VITE_GEOAPIFY_REVERSE_GEOCODING_URL=https://api.geoapify.com/v1/geocode/reverse

Clone the repository, run npm install, and npm run dev.

**WHAT I'VE LEARNED
**
I've learnt a lot from this project so far, and I've had a lot of fun developing it. It felt very satisfying to see a hashed password successfuly stored in the database, but
even moreso to be able to validate it against a plaintext password upon login! It was also cool to see how abstracted Spring is, which helped me focus more on my use cases/problem solving.
It was also cool to learn more about cookies and web tokens - specifically how HTTP is stateless, necessitating the importance of cookies.
I also saw that if you store cookies in localStorage, then you can easily access your JWT and print it in console! So that motivated me to instead use HTTP-only cookies. 


**WHAT'S NEXT? 
**
It was important for flesh out the authentication aspects before anything else, so getting my JWT tokens/password hashing was critical. Because of this, I still do have to create a front-end design
for listings, and front-end logic to support retrieval/search. There's also a bit of refining to do in general with my UI - but I'm happy with how it looks now! 

Eventually I'd love to implement OAuth, email/text notifications, and generally make the site as professional and user friendly as I can. In total, it's been a lot of fun spending countless hours
researching and learning about how sites work securely under the hood, and I'm excited to continue that journey! 


