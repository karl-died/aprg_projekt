SET MODE PostgreSQL;

CREATE TABLE profile (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    degreeCourse varchar(50) NOT NULL,
    aboutMe text NOT NULL,
    semester integer NOT NULL
);

CREATE TABLE account (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    email varchar(50) NOT NULL UNIQUE,
    password varchar(50) NOT NULL,
    firstName varchar(50) NOT NULL,
    lastName varchar(50) NOT NULL,
    dateOfBirth timestamp,
    gender varchar(20),
    profileId uuid REFERENCES profile(id) UNIQUE
);

