CREATE TABLE Artist (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

	first_name			VARCHAR(50) 	NOT NULL,
    mid_init 			CHAR(1),
    last_name 			VARCHAR(50) 	NOT NULL,
    dob 				DATE 			NOT NULL,
    phone 				VARCHAR(15),
    birth_locality		VARCHAR(25),
    birth_admin_area	VARCHAR(25),
    birth_country 		CHAR(3)
);

CREATE TABLE Address (
	artist_id 			INT UNSIGNED	NOT NULL, /*Identifying relationship*/

	premise 			VARCHAR(25),
    thoroughfare 		VARCHAR(50) 	NOT NULL,
    locality 			VARCHAR(25) 	NOT NULL,
    admin_area 			VARCHAR(25),
    country 			CHAR(3)			NOT NULL,
    postal 				VARCHAR(11)		NOT NULL,
    
    FOREIGN KEY (artist_id)		REFERENCES Artist(id)
		ON DELETE CASCADE
);

CREATE TABLE Venue (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    
    venue_name			VARCHAR(50)		NOT NULL,
	premise				VARCHAR(25),
    thoroughfare		VARCHAR(50)		NOT NULL,
    locality			VARCHAR(25)		NOT NULL,
    admin_area			VARCHAR(25),
    country				CHAR(3)			NOT NULL,
    postal				VARCHAR(11)		NOT NULL
);

CREATE TABLE Artshow (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,

    show_name 			VARCHAR(50)		NOT NULL,
    show_datetime 		DATETIME		NOT NULL,
    
    venue_id			INT UNSIGNED	NOT NULL,
    
    FOREIGN KEY (venue_id) REFERENCES Venue(id)
);

CREATE TABLE Room (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    
	room_name			VARCHAR(50)		NOT NULL,
    
    venue_id			INT UNSIGNED	NOT NULL,
    
	FOREIGN KEY (venue_id)		REFERENCES Venue(id)
		ON DELETE CASCADE
);

CREATE TABLE Contact (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    
    first_name			VARCHAR(50)		NOT NULL,
    mid_init			CHAR(1),
    last_name			VARCHAR(50)		NOT NULL,
    phone				VARCHAR(15)		NOT NULL
);

CREATE TABLE Customer (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    
    first_name			VARCHAR(50)		NOT NULL,
    mid_init			CHAR(1),
    last_name			VARCHAR(50)		NOT NULL,
    phone				VARCHAR(15)		NOT NULL
);

CREATE TABLE Style (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    
    style_name			VARCHAR(50)		NOT NULL	UNIQUE
);

CREATE TABLE Artwork (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    
    title				VARCHAR(50)		NOT NULL,
    price				DECIMAL(19, 4),
    create_date			DATE,
    acquire_date		DATE,
    
    style_id 			INT UNSIGNED,
    artist_id 			INT UNSIGNED,
    room_id				INT UNSIGNED,
    
    FOREIGN KEY (style_id)		REFERENCES Style(id)
		ON DELETE RESTRICT,
    FOREIGN KEY (artist_id) 	REFERENCES Artist(id)
		ON DELETE RESTRICT,
    FOREIGN KEY (room_id) 		REFERENCES Room(id)
		ON DELETE SET NULL,
        
	CONSTRAINT date_chk 		CHECK (create_date <= acquire_date),
    CONSTRAINT price_chk 		CHECK (price > 0)
);

CREATE TABLE Practices (
	artist_id 			INT UNSIGNED,
    style_id			INT UNSIGNED,
	
	PRIMARY KEY (artist_id, style_id),
    
	FOREIGN KEY (artist_id) 	REFERENCES Artist(id)
		ON DELETE CASCADE,
    FOREIGN KEY (style_id)		REFERENCES Style(id)
		ON DELETE CASCADE
);

CREATE TABLE Prefers (
	customer_id			INT UNSIGNED,
    style_id			INT UNSIGNED,

	PRIMARY KEY (customer_id, style_id),
    
	FOREIGN KEY (customer_id)	REFERENCES Customer(id)
		ON DELETE CASCADE,
    FOREIGN KEY (style_id)		REFERENCES Style(id)
		ON DELETE CASCADE
);

CREATE TABLE Attends (
	customer_id 		INT UNSIGNED,
    artshow_id 			INT UNSIGNED,
	
    PRIMARY KEY (customer_id, artshow_id),
    
	FOREIGN KEY(customer_id)	REFERENCES Customer(id)
		ON DELETE CASCADE,
    FOREIGN KEY(artshow_id)		REFERENCES Artshow(id)
		ON DELETE CASCADE
);

CREATE TABLE Organizes (
	artshow_id			INT UNSIGNED,
	contact_id			INT UNSIGNED,

	PRIMARY KEY (artshow_id, contact_id),
    
	FOREIGN KEY(artshow_id)		REFERENCES Artshow(id)
		ON DELETE CASCADE,
    FOREIGN KEY(contact_id)		REFERENCES Contact(id)
		ON DELETE CASCADE
);