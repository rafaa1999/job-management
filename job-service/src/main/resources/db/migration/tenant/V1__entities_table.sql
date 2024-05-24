
--CREATE TABLE car_park (
--    id UUID PRIMARY KEY,
--    car_park_number VARCHAR(255) NOT NULL UNIQUE,
--    timezone VARCHAR(255),
--    car_park_name VARCHAR(255)
--);

--CREATE SCHEMA GOOGLE;
--CREATE SCHEMA META;
--CREATE SCHEMA APPLE;

CREATE TABLE car_park (
    id UUID PRIMARY KEY,
    car_park_number VARCHAR(255) NOT NULL UNIQUE,
    timezone VARCHAR(255),
    car_park_name VARCHAR(255)
);

CREATE TABLE facility (
    id UUID PRIMARY KEY,
    car_park_id UUID REFERENCES car_park(id),
    location_id VARCHAR(255) UNIQUE,
    facility_type VARCHAR(255),
    facility_number VARCHAR(255) UNIQUE,
    facility_name VARCHAR(255),
    description TEXT,
    is_deleted BOOLEAN DEFAULT FALSE
);

--CREATE TABLE facility (
--    id UUID PRIMARY KEY,
----    carParkId UUID REFERENCES car_park(id),
--    carParkId UUID REFERENCES carPark(id),
--    locationId VARCHAR(255) UNIQUE,
--    facilityType VARCHAR(255),
--    facilityNumber VARCHAR(255) UNIQUE,
--    facilityName VARCHAR(255),
--    description TEXT,
--    isDeleted BOOLEAN DEFAULT FALSE
--);

--CREATE TABLE counter (
--    id UUID PRIMARY KEY,
--    category VARCHAR(255),
--    available INTEGER,
--    capacity INTEGER,
--    occupied INTEGER,
--    facility_id UUID REFERENCES facility(id)
--);

--CREATE TABLE counter (
--    id UUID PRIMARY KEY,
--    category VARCHAR(255),
--    available INTEGER,
--    capacity INTEGER,
--    occupied INTEGER,
--    facilityId UUID REFERENCES facility(id)
--);

--CREATE TABLE contingent (
--    id UUID PRIMARY KEY,
--    name VARCHAR(255),
--    value INTEGER,
--    start_date DATE,
--    end_date DATE,
--    day_of_week VARCHAR(255),
--    counter_id UUID REFERENCES counter(id)
--);

--CREATE TABLE contingent (
--    id UUID PRIMARY KEY,
--    name VARCHAR(255),
--    value INTEGER,
--    startDate DATE,
--    endDate DATE,
--    dayOfWeek VARCHAR(255),
--    counterId UUID REFERENCES counter(id)
--);