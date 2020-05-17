INSERT INTO Artist VALUE (NULL, 'Leonardo', 'D', 'da Vinci', '1452-01-14', '(919)-555-0100', 'Florence', 'Tuscany', 'ITA');
INSERT INTO Artist VALUE (NULL, 'Pablo', 'P', 'Picasso', '1881-10-25', '(714)-555-0101', 'Mougins', NULL, 'FRA');
INSERT INTO Artist VALUE (NULL, 'Vincent', 'W', 'van Gogh', '1853-03-30', '(714)-555-0102', 'Zundert', 'North Brabant', 'NLD');
INSERT INTO Artist VALUE (NULL, 'Michelangelo', NULL, 'di Lodovico', '1475-03-06', '(718)-555-0103', 'Caprese Michelangelo', 'Tuscany', 'ITA');

INSERT INTO Address VALUE ((SELECT	id
							FROM 	Artist
							WHERE 	first_name = 'Leonardo' AND mid_init = 'D' AND last_name = 'da Vinci'),
							'APT 555', '9883 W Valencia Dr', 'Fullerton', 'California', 'USA', '92833');
INSERT INTO Address VALUE ((SELECT	id
							FROM	Artist
							WHERE 	first_name = 'Pablo' AND mid_init = 'P' AND last_name = 'Picasso'),
							'APT 555', '9522 W Oak Ave', 'Fullerton', 'California', 'USA', '92833');
INSERT INTO Address VALUE ((SELECT	id
							FROM	Artist
							WHERE	first_name = 'Vincent' AND mid_init = 'W' AND last_name = 'van Gogh'),
							'APT 555', '9743 Palm Ave', 'Riverside', 'California', 'USA', '92506');
INSERT INTO Address VALUE ((SELECT	id
							FROM	Artist
							WHERE	first_name = 'Michelangelo' AND last_name = 'di Lodovico'),
							'APT 555', '6865 Mason Ave', 'Irvine', 'California', 'USA', '92618');

INSERT INTO Venue VALUE (NULL, 'Village Gallery At Spectrum', NULL, '510 Spectrum Center Dr', 'Irvine', 'California', 'USA', '92618');
INSERT INTO Venue VALUE (NULL, 'L.A. Louver', NULL, '45 N Venice Blvd', 'Los Angeles', 'California', 'USA', '90291');
INSERT INTO Venue VALUE (NULL, 'William Turner Gallery', NULL, '2525 Michigan Ave', 'Santa Monica', 'California', 'USA', '90404');
INSERT INTO Venue VALUE (NULL, 'Altitude Gallery', NULL, '134 E Main St', 'Bozeman', 'Montana', 'USA', '59715');

INSERT INTO Artshow VALUE (NULL, 'The da Vinci Code', '2020-04-30 17:45:00',
							(SELECT	id
                            FROM	Venue
                            WHERE	venue_name = 'William Turner Gallery' AND thoroughfare = '2525 Michigan Ave'));
INSERT INTO Artshow VALUE (NULL, 'Michelangelo Art Show', '2020-06-25 18:30:00',
							(SELECT	id
                            FROM	Venue
                            WHERE	venue_name = 'L.A. Louver' AND thoroughfare = '45 N Venice Blvd'));
INSERT INTO Artshow VALUE (NULL, 'On the Horizon', '2020-08-15 18:00:00',
							(SELECT	id
                            FROM	Venue
                            WHERE	venue_name = 'Altitude Gallery' AND thoroughfare = '134 E Main St'));
INSERT INTO Artshow VALUE (NULL, 'Picasso Art Show', '2020-10-28 19:30:00',
							(SELECT	id
                            FROM	Venue
                            WHERE	venue_name = 'Village Gallery At Spectrum' AND thoroughfare = '510 Spectrum Center Dr'));

INSERT INTO Room VALUE (NULL, 'Village Main',
						(SELECT	id
						FROM	Venue
						WHERE	venue_name = 'Village Gallery At Spectrum' AND thoroughfare = '510 Spectrum Center Dr'));
INSERT INTO Room VALUE (NULL, 'Back Room', 
						(SELECT	id
						FROM	Venue
						WHERE	venue_name = 'Village Gallery At Spectrum' AND thoroughfare = '510 Spectrum Center Dr'));
INSERT INTO Room VALUE (NULL, 'Central Room',
						(SELECT	id
						FROM	Venue
						WHERE	venue_name = 'L.A. Louver' AND thoroughfare = '45 N Venice Blvd'));
INSERT INTO Room VALUE (NULL, 'West Room',
						(SELECT	id
						FROM	Venue
						WHERE	venue_name = 'L.A. Louver' AND thoroughfare = '45 N Venice Blvd'));
INSERT INTO Room VALUE (NULL, 'Northeast Room',
						(SELECT	id
						FROM	Venue
						WHERE	venue_name = 'L.A. Louver' AND thoroughfare = '45 N Venice Blvd'));
INSERT INTO Room VALUE (NULL, 'William Turner Room',
						(SELECT	id
						FROM	Venue
						WHERE	venue_name = 'William Turner Gallery' AND thoroughfare = '2525 Michigan Ave'));
INSERT INTO Room VALUE (NULL, 'Covent Garden Room',
						(SELECT	id
						FROM	Venue
						WHERE	venue_name = 'William Turner Gallery' AND thoroughfare = '2525 Michigan Ave'));
INSERT INTO Room VALUE (NULL, 'Upper Floor',
						(SELECT	id
						FROM	Venue
						WHERE	venue_name = 'Altitude Gallery' AND thoroughfare = '134 E Main St'));
INSERT INTO Room VALUE (NULL, 'Lower Hall',
						(SELECT	id
						FROM	Venue
						WHERE	venue_name = 'Altitude Gallery' AND thoroughfare = '134 E Main St'));

INSERT INTO Contact VALUE (NULL, 'John', 'M', 'Smith', '(949)-555-0104');
INSERT INTO Contact VALUE (NULL, 'Franklin', 'B', 'Wong', '(718)-555-0105');
INSERT INTO Contact VALUE (NULL, 'Ahmad', 'C', 'Jabbar', '(562)-555-0106');
INSERT INTO Contact VALUE (NULL, 'Mohammed', 'B', 'Ali', '(714)-555-0107');

INSERT INTO Customer VALUE (NULL, 'Alicia', 'J', 'Zelaya', '(919)-555-0108');
INSERT INTO Customer VALUE (NULL, 'Ramesh', 'G', 'Narayan', '(626)-555-0109');
INSERT INTO Customer VALUE (NULL, 'James', 'J', 'Borg', '(626)-555-0110');
INSERT INTO Customer VALUE (NULL, 'Captain', 'H', 'Hook', '(909)-555-0111');

INSERT INTO Style VALUE (NULL, 'Impressionism');
INSERT INTO Style VALUE (NULL, 'Dadaism');
INSERT INTO Style VALUE (NULL, 'Expressionism');
INSERT INTO Style VALUE (NULL, 'Surrealism');
INSERT INTO Style VALUE (NULL, 'Pop Art');
INSERT INTO Style VALUE (NULL, 'Renaissance');
INSERT INTO Style VALUE (NULL, 'Cubism');
INSERT INTO Style VALUE (NULL, 'Post-Impressionism');
INSERT INTO Style VALUE (NULL, 'Fresco');
INSERT INTO Style VALUE (NULL, 'Minimalism');
INSERT INTO Style VALUE (NULL, 'Photorealism');
INSERT INTO Style VALUE (NULL, 'Pottery');
INSERT INTO Style VALUE (NULL, 'Sculpture');
INSERT INTO Style VALUE (NULL, 'Pointilism');

INSERT INTO Artwork VALUE (NULL, 'Mona Lisa', 2670000000.00, '1503-01-01', '1797-04-01',
							(SELECT	id
							FROM	Style
							WHERE	style_name = 'Renaissance'),
							(SELECT	id
                            FROM	Artist
							WHERE first_name = 'Leonardo' AND last_name = 'da Vinci'),
							(SELECT id
							FROM 	Room
                            WHERE room_name = 'Village Main' AND venue_id = 
								(SELECT id
								FROM	Venue
                                WHERE	venue_name = 'Village Gallery At Spectrum'
                                )));
INSERT INTO Artwork VALUE (NULL, 'Les Demoiselles Avigonon', 1200000000.00, '1907-01-01', '1997-11-12',
							(SELECT	id
							FROM	Style
							WHERE	style_name = 'Cubism'),
							(SELECT	id
                            FROM	Artist
							WHERE first_name = 'Pablo' AND last_name = 'Picasso'),
                            (SELECT id
							FROM 	Room
                            WHERE room_name = 'Upper Floor' AND venue_id = 
								(SELECT id
								FROM	Venue
                                WHERE	venue_name = 'Altitude Gallery'
                                )));
INSERT INTO Artwork VALUE (NULL, 'The Starry Night', 1000000000.00, '1889-06-01', '2007-07-09',
							(SELECT	id
							FROM	Style
							WHERE	style_name = 'Post-Impressionism'),
							(SELECT	id
                            FROM	Artist
							WHERE first_name = 'Vincent' AND last_name = 'van Gogh'),
                            (SELECT id
							FROM 	Room
                            WHERE room_name = 'Covent Garden Room' AND venue_id = 
								(SELECT id
								FROM	Venue
                                WHERE	venue_name = 'William Turner Gallery'
                                )));
INSERT INTO Artwork VALUE (NULL, 'David', 65000000.00, '1512-10-10', '2012-11-21',
							(SELECT	id
							FROM	Style
							WHERE	style_name = 'Sculpture'),
							(SELECT	id
                            FROM	Artist
							WHERE first_name = 'Michelangelo' AND last_name = 'di Lodovico'),
                            (SELECT id
							FROM 	Room
                            WHERE room_name = 'Central Room' AND venue_id = 
								(SELECT id
								FROM	Venue
                                WHERE	venue_name = 'L.A. Louver'
                                )));

INSERT INTO Practices VALUE ((SELECT	id
							FROM		Artist
                            WHERE first_name = 'Leonardo' AND last_name = 'da Vinci'),
							(SELECT		id
							FROM		Style
							WHERE style_name = 'Renaissance')); 
INSERT INTO Practices VALUE ((SELECT	id
							FROM		Artist
                            WHERE first_name = 'Pablo' AND last_name = 'Picasso'),
							(SELECT		id
							FROM		Style
							WHERE style_name = 'Cubism')); 
INSERT INTO Practices VALUE ((SELECT	id
							FROM		Artist
                            WHERE first_name = 'Vincent' AND last_name = 'van Gogh'),
							(SELECT		id
							FROM		Style
							WHERE style_name = 'Post-Impressionism')); 
                            INSERT INTO Practices VALUE ((SELECT	id
							FROM		Artist
                            WHERE first_name = 'Vincent' AND last_name = 'van Gogh'),
							(SELECT		id
							FROM		Style
							WHERE style_name = 'Pointilism')); 
INSERT INTO Practices VALUE ((SELECT	id
							FROM		Artist
                            WHERE first_name = 'Michelangelo' AND last_name = 'di Lodovico'),
							(SELECT		id
							FROM		Style
							WHERE style_name = 'Renaissance')); 
INSERT INTO Practices VALUE ((SELECT	id
							FROM		Artist
                            WHERE first_name = 'Michelangelo' AND last_name = 'di Lodovico'),
							(SELECT		id
							FROM		Style
							WHERE style_name = 'Sculpture')); 

INSERT INTO Prefers VALUE ((SELECT		id
							FROM		Customer
							WHERE		first_name = 'Alicia' AND last_name = 'Zelaya'),
							(SELECT		id
							FROM		Style
							WHERE style_name = 'Pop Art'));
INSERT INTO Prefers VALUE ((SELECT		id
							FROM		Customer
							WHERE		first_name = 'Ramesh' AND last_name = 'Narayan'),
							(SELECT		id
							FROM		Style
							WHERE style_name = 'Renaissance'));
INSERT INTO Prefers VALUE ((SELECT		id
							FROM		Customer
							WHERE		first_name = 'James' AND last_name = 'Borg'),
							(SELECT		id
							FROM		Style
							WHERE style_name = 'Renaissance'));
INSERT INTO Prefers VALUE ((SELECT		id
							FROM		Customer
							WHERE		first_name = 'Captain' AND last_name = 'Hook'),
							(SELECT		id
							FROM		Style
							WHERE style_name = 'Impressionism'));

INSERT INTO Attends VALUE ((SELECT	id
							FROM	Customer
							WHERE	first_name = 'Ramesh' AND last_name = 'Narayan'),
                            (SELECT	id
							FROM	Artshow
							WHERE	show_name = 'The da Vinci Code' AND show_datetime = '2020-04-30 17:45:00'));
INSERT INTO Attends VALUE ((SELECT	id
							FROM	Customer
							WHERE	first_name = 'Alicia' AND last_name = 'Zelaya'),
                            (SELECT	id
							FROM	Artshow
							WHERE	show_name = 'Picasso Art Show' AND show_datetime = '2020-10-28 19:30:00'));
INSERT INTO Attends VALUE ((SELECT	id
							FROM	Customer
							WHERE	first_name = 'James' AND last_name = 'Borg'),
							(SELECT	id
							FROM	Artshow
							WHERE	show_name = 'Michelangelo Art Show' AND show_datetime = '2020-06-25 18:30:00'));
INSERT INTO Attends VALUE ((SELECT	id
							FROM	Customer
							WHERE	first_name = 'Captain' AND last_name = 'Hook'),
							(SELECT	id
							FROM	Artshow
							WHERE	show_name = 'On the Horizon' AND show_datetime = '2020-08-15 18:00:00'));

INSERT INTO Organizes VALUE ((SELECT	id
							FROM		Artshow
							WHERE		show_name = 'On the Horizon' AND show_datetime = '2020-08-15 18:00:00'),
                            (SELECT		id
							FROM		Contact
							WHERE		first_name = 'Ahmad' AND last_name = 'Jabbar'));
INSERT INTO Organizes VALUE ((SELECT	id
							FROM		Artshow
							WHERE		show_name = 'Michelangelo Art Show' AND show_datetime = '2020-06-25 18:30:00'),
                            (SELECT		id
							FROM		Contact
							WHERE		first_name = 'Franklin' AND last_name = 'Wong'));
INSERT INTO Organizes VALUE ((SELECT	id
							FROM		Artshow
							WHERE		show_name = 'Picasso Art Show' AND show_datetime = '2020-10-28 19:30:00'),
                            (SELECT		id
							FROM		Contact
							WHERE		first_name = 'John' AND last_name = 'Smith'));
INSERT INTO Organizes VALUE ((SELECT	id
							FROM		Artshow
							WHERE		show_name = 'The da Vinci Code' AND show_datetime = '2020-04-30 17:45:00'),
                            (SELECT		id
							FROM		Contact
							WHERE		first_name = 'Mohammed' AND last_name = 'Ali'));