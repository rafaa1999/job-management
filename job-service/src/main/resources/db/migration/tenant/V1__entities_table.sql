
CREATE TABLE car_park (
    id UUID PRIMARY KEY,
--    car_park_number VARCHAR(255) NOT NULL UNIQUE,
    car_park_number VARCHAR(255),
    timezone VARCHAR(255),
    car_park_name VARCHAR(255)
);

CREATE TABLE facility (
    id UUID PRIMARY KEY,
    car_park_id UUID REFERENCES car_park(id),
--    location_id VARCHAR(255) UNIQUE,
    location_id VARCHAR(255),
    facility_type VARCHAR(255),
--    facility_number VARCHAR(255) UNIQUE,
    facility_number VARCHAR(255),
    facility_name VARCHAR(255),
    description TEXT,
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE counter (
    id UUID PRIMARY KEY,
    category VARCHAR(255),
    available INTEGER,
    capacity INTEGER,
    occupied INTEGER,
    facility_id UUID REFERENCES facility(id)
);

CREATE TABLE contingent (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
--    value INTEGER,
    normal_value INTEGER,
    weekend_value INTEGER,
--    start_date DATE,
--    end_date DATE,
    start_date Date,
    end_date Date,
    start_day_of_week Date,
    end_day_of_week Date,
    car_park_id UUID,
    facility_id UUID
--    counter_id UUID REFERENCES counter(id)
);

CREATE TABLE shedlock(
    name VARCHAR(64) NOT NULL,
    lock_until TIMESTAMP NOT NULL,
    locked_at TIMESTAMP NOT NULL,
    locked_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE history (
    id UUID PRIMARY KEY,
    job_name VARCHAR(255),
    status VARCHAR(255),
    scheduled_fire_time VARCHAR(255),
    previous_fire_time VARCHAR(255),
    next_fire_time VARCHAR(255)
);
