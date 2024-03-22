INSERT INTO cars(id, model, brand, type, inventory, daily_fee)
VALUES (1, 'model', 'brand', 'SEDAN', 10, 25.50);

INSERT INTO users (id, email, first_name, last_name, password, role)
VALUES (1,'email1@gmail.com', 'John', 'Smith', 'ldnjkabvsknc', 'CUSTOMER');

INSERT INTO rentals (id, rental_date, return_date, car_id, user_id)
VALUES (1, '2024-03-01', '2024-03-10', 1, 1);

INSERT INTO rentals (id, rental_date, return_date, car_id, user_id)
VALUES (2, '2024-03-05', '2024-03-25', 1, 1);