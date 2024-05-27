SET MODE PostgreSQL;

CREATE TABLE account (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    email varchar(50) NOT NULL UNIQUE,
    password varchar(100) NOT NULL
);

CREATE TABLE profile (
     id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
     firstName varchar(50) NOT NULL,
     lastName varchar(50) NOT NULL,
     dateOfBirth timestamp,
     gender varchar(20),
     degreeCourse varchar(50) NOT NULL,
     aboutMe text NOT NULL,
     semester integer NOT NULL,
     accountId uuid REFERENCES account(id) NOT NULL UNIQUE
);

CREATE TABLE authority (
    role varchar(100) NOT NULL,
    account uuid REFERENCES account(id) NOT NULL,
    PRIMARY KEY (role, account)
);