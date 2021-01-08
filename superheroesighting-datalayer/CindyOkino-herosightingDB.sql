DROP DATABASE IF EXISTS heroSightingDb ;

CREATE DATABASE heroSightingDb ;

USE heroSightingDb ;

-- ====================== MAIN TABLES ===========================
CREATE TABLE hero_villain(
    id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
    `description` VARCHAR(50)
);

CREATE TABLE location(
	id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
    `description` VARCHAR(50),
    address VARCHAR(50) NOT NULL,
    latitude DECIMAL(10,8) NOT NULL,
    longitude DECIMAL(11,8) NOT NULL
);

CREATE TABLE superpower(
	id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
    `description` VARCHAR(50)    
);

CREATE TABLE `organization`(
	id INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
    `description` VARCHAR(50),
    address VARCHAR(50),
    contact VARCHAR(50)
);

-- ====================== BRIDGE TABLES ===========================
CREATE TABLE sighting(
	hero_villain_id INT NOT NULL,
    location_id INT NOT NULL,
    PRIMARY KEY pk_hero_villain_sighting (hero_villain_id, location_id),
    FOREIGN KEY (hero_villain_id) REFERENCES hero_villain(id),
    FOREIGN KEY (location_id) REFERENCES location(id),
    `date` DATE NOT NULL
);

CREATE TABLE hero_villain_superpower(
	hero_villain_id INT NOT NULL,
    superpower_id INT NOT NULL,
    PRIMARY KEY pk_hero_villain_sighting (hero_villain_id, superpower_id),
    FOREIGN KEY (hero_villain_id) REFERENCES hero_villain(id),
    FOREIGN KEY (superpower_id) REFERENCES superpower(id)
);

CREATE TABLE hero_villain_organization(
	hero_villain_id INT NOT NULL,
    organization_id INT NOT NULL,
    PRIMARY KEY pk_hero_villain_sighting (hero_villain_id, organization_id),
    FOREIGN KEY (hero_villain_id) REFERENCES hero_villain(id),
    FOREIGN KEY (organization_id) REFERENCES `organization`(id)
);

