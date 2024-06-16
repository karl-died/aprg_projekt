SET MODE PostgreSQL;

CREATE TABLE account (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    email varchar(50) NOT NULL UNIQUE,
    password varchar(100) NOT NULL
);

CREATE TABLE gender (
    id INTEGER PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

INSERT INTO gender VALUES
    (1, 'MALE'),
    (2, 'FEMALE'),
    (3, 'DIVERSE')
;

CREATE TABLE profile (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    firstName varchar(50) NOT NULL,
    lastName varchar(50) NOT NULL,
    dateOfBirth timestamp,
    genderId INTEGER REFERENCES gender(id),
    degreeCourse varchar(50) NOT NULL,
    aboutMe text NOT NULL,
    semester integer NOT NULL,
    accountId uuid REFERENCES account(id) NOT NULL UNIQUE,
    profilePictureName varchar(100)
);

CREATE TABLE r_interested_in (
    accountId uuid REFERENCES account(id) NOT NULL,
    genderId INTEGER REFERENCES gender(id) NOT NULL,
    PRIMARY KEY(accountId, genderId)
);

CREATE TABLE authority (
    role varchar(100) NOT NULL,
    account uuid REFERENCES account(id) NOT NULL,
    PRIMARY KEY (role, account)
);

CREATE TABLE r_rating (
    accountId uuid REFERENCES account(id) NOT NULL,
    ratedId uuid REFERENCES account(id) NOT NULL,
    isLike boolean NOT NULL,
    PRIMARY KEY(accountId, ratedId)
);

CREATE TABLE image (
    name varchar(100) PRIMARY KEY,
    index serial,
    profileId uuid REFERENCES profile(id) NOT NULL
);

CREATE VIEW account_profile AS
    SELECT account.id AS accountId, profile.id AS profileId, email, firstName, lastName, dateOfBirth, genderId, degreeCourse, aboutMe, semester
    FROM account LEFT JOIN profile ON account.id = profile.accountId;

CREATE VIEW profile_image AS
    SELECT profile.id AS id,
           firstName,
           lastName,
           dateOfBirth,
           gender.name AS "GENDER",
           array(
                SELECT name
                FROM r_interested_in
                WHERE r_interested_in.accountId = profile.accountId
           ) AS interestedIn,
           degreeCourse,
           aboutMe,
           semester,
           profilePictureName,
           accountId,
           array(
                SELECT name
                FROM image
                WHERE image.profileId = profile.id
                ORDER BY index
           ) AS imageNames
    FROM profile
    JOIN gender ON profile.genderId = gender.id;

CREATE TABLE chat (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    accountId1 uuid REFERENCES account(id) NOT NULL,
    accountId2 uuid REFERENCES account(id) NOT NULL
);

CREATE TABLE chatMessage (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    chatId uuid REFERENCES chat(id) NOT NULL,
    senderId uuid REFERENCES account(id) NOT NULL,
    message TEXT,
    dateSent TIMESTAMP NOT NULL
);

