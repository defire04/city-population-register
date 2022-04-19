DROP TABLE IF EXISTS city_register_address_person;
DROP TABLE IF EXISTS city_register_person;
DROP TABLE IF EXISTS city_register_address;
DROP TABLE IF EXISTS city_register_district;
DROP TABLE IF EXISTS city_register_street;

CREATE TABLE city_register_district (
    district_code INTEGER NOT NULL,
    district_name VARCHAR (300),
    PRIMARY KEY (district_code)
);
INSERT INTO city_register_district(district_code, district_name) VALUES (1, 'Святошинский');


CREATE TABLE city_register_street(
    street_code INTEGER NOT NULL,
    street_name VARCHAR (300),
    PRIMARY KEY (street_code)
);
INSERT INTO city_register_street(street_code, street_name) VALUES (1, 'Якуба Коласа');


CREATE TABLE city_register_address(
    address_id SERIAL,
    district_code INTEGER NOT NUll,
    street_code INTEGER NOT NULL,
    building VARCHAR(10) NOT NULL,
    extension VARCHAR(10) NOT NULL,
    apartment VARCHAR(10) NOT NUll,

    PRIMARY KEY (address_id),
    FOREIGN KEY (district_code) REFERENCES city_register_district(district_code) ON DELETE RESTRICT,
    FOREIGN KEY (street_code) REFERENCES city_register_street(street_code) ON DELETE RESTRICT
);
INSERT INTO city_register_address(district_code, street_code, building, extension, apartment) VALUES
(1, 1, '2', '6', '333') ;
(1, 1, '271', null, '4') ;


CREATE TABLE city_register_person(
    person_id SERIAL,
    sur_name VARCHAR(100) NOT NULL,
    given_name VARCHAR(100) NOT NULL,
    patronymic VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    passport_series VARCHAR(10),
    passport_number VARCHAR(10),
    passport_date DATE,
    certificate_number VARCHAR(10),
    certificate_date DATE,
    PRIMARY KEY (person_id)
);
INSERT INTO city_register_person (sur_name, given_name, patronymic, date_of_birth, passport_series, passport_number,
passport_date, certificate_number, certificate_date) VALUES
('Соклов', 'Дмитрий', 'Романович', '2000-05-07', '1234','123456', '2018-06-13', null, null),
('Соклова', 'София', 'Олеговна', '2001-09-10', '4321','654321', '2018-10-16', null, null),
('Соклов', 'Максим', 'Дмитриевич', '2021-11-11', null,null, null, '1234', '2015-11-21');


CREATE TABLE city_register_address_person(
    person_address_id SERIAL,
    address_id INTEGER NOT NUll,
    person_id INTEGER NOT NUll,
    start_date DATE NOT NULL,
    end_date DATE,
    temporal boolean DEFAULT FALSE,


    PRIMARY KEY (person_address_id),
    FOREIGN KEY (address_id) REFERENCES city_register_address(address_id) ON DELETE RESTRICT,
    FOREIGN KEY (person_id) REFERENCES city_register_person(person_id) ON DELETE RESTRICT
);
INSERT INTO city_register_address_person(address_id, person_id, start_date, end_date, temporal) VALUES
(1, 1, '2019-01-03', null, false),
(2, 2, '2019-01-03', null, false),
(1, 3, '2021-11-21', null, false);


/*

SELECT * FROM city_register_district;
SELECT * FROM city_register_street;
SELECT * FROM city_register_address;
SELECT * FROM city_register_person;
SELECT * FROM city_register_address_person;

*/
