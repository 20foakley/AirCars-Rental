-- Frederick Oakley

create schema if not exists p2p_car_rental;

SET search_path TO p2p_car_rental;


CREATE TYPE fuel_type AS ENUM ('regular', 'premium', 'plus', 'hydrogen', 'whatever I find in the shed', 'diesel', 'electric');
CREATE TYPE transmission_type AS ENUM ('automatic', 'manual', 'cvt', 'hopes and dreams');

DELETE FROM vehicles;
select * from vehicles;

CREATE table if not exists p2p_car_rental.users
(
    id serial primary KEY,
    email character varying(60) NOT NULL UNIQUE,
    username character varying(20) NOT NULL UNIQUE,
    first_name character varying(20),
    password character varying(100) NOT NULL,
	is_renter boolean NOT null DEFAULT TRUE,
    is_admin boolean NOT null DEFAULT FALSE,
	date_of_birth date,
	street_address VARCHAR(100),
    city VARCHAR(50),
    state_province VARCHAR(50),
    postal_code VARCHAR(20),
    country VARCHAR(50),
    latitude DECIMAL(8,6),
    longitude DECIMAL(9,6),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CHECK (latitude >= -90 AND latitude <= 90),
	CHECK (longitude >= -180 AND longitude <= 180)

);

CREATE TABLE if not exists p2p_car_rental.vehicles
(
    id serial NOT NULL,
    make character varying(20) NOT NULL,
    model character varying(30) NOT NULL,
	submodel character varying(30),
    year real NOT NULL,
    mpg JSONB,
    trims JSONB,
    doors JSONB,
    color JSONB,
    fuel fuel_type,
    horsepower real,
    transmission transmission_type,
    PRIMARY KEY (id),
	UNIQUE (year, make, model),
	CHECK (year >= 1900 AND year <= EXTRACT(YEAR FROM CURRENT_DATE) + 1)
);

CREATE table if not EXISTS p2p_car_rental.listings
(
    id serial NOT NULL,
    vin character varying(25) NOT NULL UNIQUE,
    vehicle_id int NOT NULL,
    owner_user_id int NOT NULL,
	vehicle_value real NOT NULL,
	city VARCHAR(50) NOT NULL,
	country VARCHAR(50) NOT NULL,
    latitude DECIMAL(8,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    max_duration smallint NOT NULL,
    created_at date NOT NULL DEFAULT current_date,
    updated_at date NOT NULL,
    title character varying(20) NOT NULL,
    description character varying(600) NOT NULL,
    PRIMARY KEY (id),
	FOREIGN KEY (owner_user_id) REFERENCES  p2p_car_rental.users (id) on delete cascade;
	FOREIGN KEY (vehicle_id) REFERENCES  p2p_car_rental.vehicles (id) on delete cascade;
); 


CREATE table if not EXISTS p2p_car_rental.rentals
(
    id serial NOT NULL,
    renter_user_id int NOT NULL,
    owner_user_id int NOT NULL,
	listing_id int NOT NULL,
	start_date date not null,
    end_date date not null,
    created_at date NOT NULL DEFAULT current_date,
    updated_at date,
    daily_fee real NOT NULL,
	fee_prctg real NOT NULL,
	protection_fee real,
    PRIMARY KEY (id),
	CHECK (daily_fee >= 10),
	CHECK (fee_prctg > 0),
	CONSTRAINT different_users CHECK (renter_user_id <> owner_user_id),
    FOREIGN KEY (renter_user_id) REFERENCES  p2p_car_rental.users (id),
    FOREIGN KEY (owner_user_id) REFERENCES  p2p_car_rental.users (id),
	FOREIGN KEY (listing_id) REFERENCES  p2p_car_rental.listings (id)
);
	


CREATE table if not EXISTS p2p_car_rental.reviews
(
	id serial NOT NULL,
	reviewer_user_id int NOT NULL,
	reviewed_user_id int NOT NULL,
	listing_id int NOT NULL,
	title character varying(20) NOT NULL,
	description character varying(600) NOT NULL,
	created_at date NOT NULL DEFAULT current_date,
	PRIMARY KEY (id),
	CONSTRAINT different_users CHECK (reviewer_user_id <> reviewed_user_id),
    FOREIGN KEY (reviewer_user_id) REFERENCES  p2p_car_rental.users (id),
    FOREIGN KEY (reviewed_user_id) REFERENCES  p2p_car_rental.users (id),
	FOREIGN KEY (listing_id) REFERENCES  p2p_car_rental.listings (id)
);

CREATE table if not EXISTS p2p_car_rental.listing_images
(
    id serial NOT NULL,
    FOREIGN KEY listings_id serial REFERENCES listings(id) on delete cascade;
    image_url TEXT not null;
);

END;
