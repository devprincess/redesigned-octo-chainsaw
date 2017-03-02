
# --- Sample dataset

# --- !Ups

INSERT INTO paymethod(name)	VALUES ('None');
INSERT INTO paymethod(name)	VALUES ('Visa');
INSERT INTO paymethod(name)	VALUES ('Mastercard');

INSERT INTO shippingmethod(name) VALUES ('UPS');
INSERT INTO shippingmethod(name) VALUES ('Fedex');

INSERT INTO category(name, views, url) VALUES   ('Books', 0, '/assets/images/books.jpg');
INSERT INTO category(name, views, url) VALUES	('Electronics', 0, '/assets/images/books.jpg');
INSERT INTO category(name, views, url) VALUES	('Fashion', 0, '/assets/images/books.jpg');
INSERT INTO category(name, views, url) VALUES	('Home', 0, '/assets/images/books.jpg');

INSERT INTO role(name) VALUES ('admin');
INSERT INTO role(name) VALUES ('employee');
INSERT INTO role(name) VALUES ('client');


INSERT INTO user(name, mobile, email, pwd, gender, birthdate, idpaymethod, address, idrole) VALUES ('Joana Chavez', '3489245521', 'admin', '123456', 'F', '1988-08-10', 1, '', 1);
INSERT INTO user(name, mobile, email, pwd, gender, birthdate, idpaymethod, address, idrole) VALUES ('Maria Esther La Valle', '6786786781', 'mariaestherlavalle@hotmail.com', '123456', 'F', '1988-06-05', 1,'', 1);
INSERT INTO user(name, mobile, email, pwd, gender, birthdate, idpaymethod, address, idrole) VALUES ('Cliente', '5589245521', 'cliente', '123456', 'F', '1988-04-23', 1,'', 3);
INSERT INTO user(name, mobile, email, pwd, gender, birthdate, idpaymethod, address, idrole) VALUES ('Empleado', '7789255521', 'empleado', '123456', 'F', '1988-01-30', 1,'', 2);

INSERT INTO product(name, description, price, idcategory, views, url) VALUES ('Harry Potter: Harry Potter and the Sorcerers Stone', 'Harry Potter has never even heard of Hogwarts when the letters start dropping on the doormat at number four, Privet Drive. Addressed in green ink on yellowish parchment with a purple seal, they are swiftly confiscated by his grisly aunt and uncle. Then, on Harrys eleventh birthday, a great beetle-eyed giant of a man called Rubeus Hagrid bursts in with some astonishing news: Harry Potter is a wizard, and he has a place at Hogwarts School of Witchcraft and Wizardry. An incredible adventure is about to begin!', 23.00, 1, 0, '/assets/images/books.jpg');
INSERT INTO product(name, description, price, idcategory, views, url) VALUES ('Harry Potter: Harry Potter and the Cursed Child','Based on an original new story by J.K. Rowling, John Tiffany and Jack Thorne, a new play by Jack Thorne, Harry Potter and the Cursed Child is the eighth story in the Harry Potter series and the first official Harry Potter story to be presented on stage. The play will receive its world premiere in Londons West End on 30th July 2016.', 30.00, 1, 0,  '/assets/images/camera.jpg');
INSERT INTO product(name, description, price, idcategory, views, url) VALUES ('Harry Potter: Harry Potter and the Chamber of Secrets','Harry Potters summer has included the worst birthday ever, doomy warnings from a house-elf called Dobby, and rescue from the Dursleys by his friend Ron Weasley in a magical flying car! Back at Hogwarts School of Witchcraft and Wizardry for his second year, Harry hears strange whispers echo through empty corridors - and then the attacks start. Students are found as though turned to stone . Dobbys sinister predictions seem to be coming true.', 35.20, 1, 0,  '/assets/images/camera.jpg');
INSERT INTO product(name, description, price, idcategory, views, url) VALUES ('Harry Potter: Harry Potter and the Half Blood Prince','When Dumbledore arrives at Privet Drive one summer night to collect Harry Potter, his wand hand is blackened and shrivelled, but he does not reveal why. Secrets and suspicion are spreading through the wizarding world, and Hogwarts itself is not safe. Harry is convinced that Malfoy bears the Dark Mark: there is a Death Eater amongst them. Harry will need powerful magic and true friends as he explores Voldemort darkest secrets, and Dumbledore prepares him to face his destiny.', 33.50, 1, 0, '/assets/images/camera.jpg');

INSERT INTO product(name, description, price, idcategory, views, url) VALUES ('Bluetooth Speaker','PartyUp from Ultimate Ears allows you to wirelessly connect more than 50 UE speakers',90.00,2, 0,  '/assets/images/camera.jpg');
INSERT INTO product(name, description, price, idcategory, views, url) VALUES ('Samsung Galaxy S3 8GB','Display: 4.8-inches - Camera: 8-MP - Processor Speed: 1.5 GHz - OS: Android 4.1 (Jelly Bean)',93.00,2, 0,  '/assets/images/camera.jpg');
INSERT INTO product(name, description, price, idcategory, views, url) VALUES ('Apple iPhone 4S 8GB','Resolution 640 x 960 pixels (~330 ppi pixel density) - Protection Corning Gorilla Glass, oleophobic coating',70.00,2, 0,  '/assets/images/camera.jpg');

INSERT INTO product(name, description, price, idcategory, views, url) VALUES ('Shirt 1','A CLASSIC LOOK FOR EVERYONE: Stanzino presents a classic must have for women everywhere. Our Perfect Fit V Neck Tunic is an essential addition to your wardrobe. Ideal for women of all sizes, this fashionable tunic has a distinct silhouette featuring a ¾ sleeve, V-Neckline, and a high-low hem that drapes low along the back.', 20.00, 3, 0,  '/assets/images/camera.jpg');
INSERT INTO product(name, description, price, idcategory, views, url) VALUES ('Shirt 2','A CLASSIC LOOK FOR EVERYONE: Stanzino presents a classic must have for women everywhere. Our Perfect Fit V Neck Tunic is an essential addition to your wardrobe. Ideal for women of all sizes, this fashionable tunic has a distinct silhouette featuring a ¾ sleeve, V-Neckline, and a high-low hem that drapes low along the back.', 25.00, 3, 0,  '/assets/images/camera.jpg');
INSERT INTO product(name, description, price, idcategory, views, url) VALUES ('Pants','A CLASSIC LOOK FOR EVERYONE: Stanzino presents a classic must have for women everywhere. Our Perfect Fit V Neck Tunic is an essential addition to your wardrobe. Ideal for women of all sizes, this fashionable tunic has a distinct silhouette featuring a ¾ sleeve, V-Neckline, and a high-low hem that drapes low along the back.', 50.00, 3, 0,  '/assets/images/camera.jpg');

INSERT INTO product(name, description, price, idcategory, views, url) VALUES ('Table','48-inch x 24-inch molded tabletop', 56.00, 4, 0,  '/assets/images/camera.jpg');
INSERT INTO product(name, description, price, idcategory, views, url) VALUES ('Office chair', 'Multi-colored bonded leather high back gaming chair task chair with pneumatic seat height adjustment and extra footrest.', 40.00, 4, 0,  '/assets/images/camera.jpg');
INSERT INTO product(name, description, price, idcategory, views, url) VALUES ('Sofa','Contemporary design coordinates well with any environment',120.00, 4, 0,  '/assets/images/camera.jpg');


INSERT INTO stock(idproduct,quantity) VALUES (1,1);
INSERT INTO stock(idproduct,quantity) VALUES (2,3);
INSERT INTO stock(idproduct,quantity) VALUES (3,0);
INSERT INTO stock(idproduct,quantity) VALUES (4,1);

INSERT INTO stock(idproduct,quantity) VALUES (5,10);
INSERT INTO stock(idproduct,quantity) VALUES (6,0);
INSERT INTO stock(idproduct,quantity) VALUES (7,5);

INSERT INTO stock(idproduct,quantity) VALUES (8,4);
INSERT INTO stock(idproduct,quantity) VALUES (9,5);
INSERT INTO stock(idproduct,quantity) VALUES (10,3);

INSERT INTO stock(idproduct,quantity) VALUES (11,0);
INSERT INTO stock(idproduct,quantity) VALUES (12,2);
INSERT INTO stock(idproduct,quantity) VALUES (13,1);

# --- !Downs

DELETE FROM paymethod;
DELETE FROM role;
DELETE FROM category;
DELETE FROM user;
DELETE FROM product;
DELETE FROM stock;
DELETE FROM shippingmethod;


