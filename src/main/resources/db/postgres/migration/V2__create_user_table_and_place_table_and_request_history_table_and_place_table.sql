CREATE TABLE IF NOT EXISTS "users"
(
    id serial PRIMARY KEY,
    local_id varchar(63) not null unique,
    email varchar(255) unique not null,
    created_date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS "interests"
(
    id serial PRIMARY KEY,
    interest varchar(31) not null
);

CREATE TABLE IF NOT EXISTS "users_interests_table"
(
    id serial PRIMARY KEY,
    user_id int not null,
    interest_id int not null,
    FOREIGN KEY (user_id)
        REFERENCES users (id),
    FOREIGN KEY (interest_id)
        REFERENCES interests (id)
);

CREATE INDEX users_interests_table_user_id_index ON users_interests_table (user_id);

CREATE TABLE IF NOT EXISTS "places"
(
    id serial PRIMARY KEY,
    name varchar(255) unique not null,
    types text[],
    rating float,
    place_id varchar(255) not null,
    created_date TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS "request_histories"
(
    id serial PRIMARY KEY,
    user_id int not null,
    place_id int not null,
    longitude float not null,
    latitude float not null,
    mode varchar(31) not null,
    created_date TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (id),
    FOREIGN KEY (place_id)
        REFERENCES places (id)
);