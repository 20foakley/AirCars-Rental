--Frederick Oakley
-- 12/8/2025 

create schema if not exists p2p_car_rental;

set search_path to p2p_car_rental;

select * from p2p_car_rental.users;


create type fuel_type as enum ('regular', 'premium', 'plus', 'diesel', 'electric');
create type transmission_type as enum ('automatic', 'manual', 'cvt');
create type vehicle_type as enum ('Sedan','Crossover','SUV','Pickup Truck','Hatchback','Minivan','Van','Convertible','Wagon','Sports Car','Recreational Vehicle');


create table if not exists p2p_car_rental.users
(
    id bigserial primary key,
    email varchar(60) not null unique,
    username varchar(20) not null unique,
    first_name varchar(30),
    last_name varchar(40),
    password varchar(255) not null,
	is_renter boolean not null default true,
    is_admin boolean not null default false,
	date_of_birth date,
	street_address varchar(100),
    city varchar(50),
    state_province varchar(50),
    postal_code varchar(20),
    country varchar(50),
    latitude decimal(8,6),
    longitude decimal(9,6),
	created_at timestamptz not null default now(),
	updated_at timestamptz not null default now(),
    check (latitude is null or latitude >= -90 and latitude <= 90),
	check (longitude is null or longitude >= -180 and longitude <= 180)

);

create table if not exists p2p_car_rental.vehicles
(
    id bigserial primary key,
    vin varchar(25) not null unique,
    make varchar(30) not null,
    model varchar(30) not null,
	submodel varchar(40),
	vehicle_type vehicle_type,
    year smallint not null,
    city_mpg smallint,
    highway_mpg smallint,
    combined_mpg smallint,
    trim varchar(30),
    doors smallint,
    color varchar(30),
    fuel fuel_type,
    horsepower smallint,
    transmission transmission_type,
	created_at timestamptz not null default now(),
	updated_at timestamptz not null default now(),
	check (year >= 1900 and year <= extract(year from current_date) + 1),
	check (doors is null or doors > 0),
	check (horsepower is null or horsepower > 0)
);

create table if not exists p2p_car_rental.listings
(
    id bigserial primary key,
    vehicle_id bigint not null references  p2p_car_rental.vehicles (id) on delete cascade,
    owner_id bigint not null references  p2p_car_rental.users (id) on delete cascade,
	city varchar(50) not null,
	country varchar(50) not null,
    latitude decimal(8,6) not null,
    longitude decimal(9,6) not null,
    max_duration smallint not null,
	created_at timestamptz not null default now(),
	updated_at timestamptz not null default now(),
    title varchar(20) not null,
    description varchar(600) not null,
    vehicle_value decimal(10,2) not null,
    currency char(3) not null default 'USD',
   	unique(vehicle_id),
   	check (currency in ('USD','EUR')),
   	check (vehicle_value < 200000),
   	check (max_duration > 0 and max_duration <= 90),
    check (latitude >= -90 and latitude <= 90),
	check (longitude >= -180 and longitude <= 180)
); 


create table if not exists p2p_car_rental.rentals
(
    id bigserial primary key,
    renter_id bigint not null references  p2p_car_rental.users (id),
    owner_id bigint not null references  p2p_car_rental.users (id),
	listing_id bigint not null references  p2p_car_rental.listings (id)  on delete cascade,
	start_date date not null,
    end_date date not null,
	created_at timestamptz not null default now(),
	updated_at timestamptz not null default now(),
    daily_fee numeric(10,2) not null,
	fee_percentage numeric(5,4) not null,
	deposit numeric(10,2) not null,
	check (daily_fee >= 10),
	check (fee_percentage > 0 and fee_percentage <=1),
	check (deposit > 0),
	check (start_date < end_date),
	constraint different_users check (renter_id <> owner_id)
);
	


create table if not exists p2p_car_rental.reviews
(
	id bigserial primary key,
	reviewer_id bigint not null references  p2p_car_rental.users (id),
	reviewee_id bigint not null references  p2p_car_rental.users (id),
	listing_id bigint not null  references  p2p_car_rental.listings (id),
	title varchar(20) not null,
	description varchar(600) not null,
	created_at timestamptz not null default now(),
	updated_at timestamptz not null default now(),
	constraint different_users check (reviewer_id <> reviewee_id)
);

create table if not exists p2p_car_rental.listing_images
(
    id bigserial primary key,
    listing_id bigint not null references p2p_car_rental.listings (id) on delete cascade,
    image_url text not null
);

