
CREATE TABLE car_park (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    car_park_number VARCHAR(255) NOT NULL UNIQUE,
    timezone VARCHAR(255),
    car_park_name VARCHAR(255)
);

CREATE TABLE facility (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    car_park_id UUID REFERENCES car_park(id),
    location_id VARCHAR(255) UNIQUE,
    facility_type VARCHAR(255),
    facility_number VARCHAR(255) UNIQUE,
    facility_name VARCHAR(255),
    description TEXT,
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE counter (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    category VARCHAR(255),
    available INTEGER,
    capacity INTEGER,
    occupied INTEGER,
    facility_id UUID REFERENCES facility(id)
);

CREATE TABLE contingent (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255),
    value INTEGER,
    start_date DATE,
    end_date DATE,
    day_of_week VARCHAR(255),
    counter_id UUID REFERENCES counter(id)
);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";