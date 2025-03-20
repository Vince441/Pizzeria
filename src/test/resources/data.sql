INSERT INTO CLIENT (nom, prenom, email) VALUES
('THEBAULT', 'Elian', 'elian@mail.fr'),
('LOUVART DE PONTLEVOYE', 'Vincent', 'vincent@mail.fr');


INSERT INTO INGREDIENT (nom, stock) VALUES
('Pepperoni', 50),
('Mozzarella', 35),
('Ananus', 40);

INSERT INTO PIZZA (nom)
VALUES
    ('4 fromages'),
    ('Saumon Buratta');


INSERT INTO pizza_prix (pizza_id, tarif_taille_key, prix)
VALUES
    (1, 'PETITE', 7),
    (1, 'MOYENNE', 13),
    (1, 'GRANDE', 17),
    (2, 'PETITE', 8),
    (2, 'MOYENNE', 15),
    (2, 'GRANDE', 20);