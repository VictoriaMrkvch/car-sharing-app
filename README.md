# Car Sharing Service project description

There is a car sharing service, where you can rent cars and pay for your usage, depending on the
duration of your rental.

### Technologies and Tools Used:
1. Spring Boot v3.1.8
2. Spring Security
3. Spring Data JPA
4. Swagger
5. MySQL v8.0.33
6. Docker
7. Java v17

### Architecture
![img.png](architecture.png)

### Models

1. Car:
    - Model: String
    - Brand: String
    - Type: Enum: SEDAN | SUV | HATCHBACK | UNIVERSAL
    - Inventory (the number of this specific car available for now in the car sharing service): int
    - Daily fee: decimal (in $USD)
2. User (Customer):
    - Email: String
    - First name: String
    - Last name: String
    - Password: String
    - Role: Enum: MANAGER | CUSTOMER
3. Rental:
    - Rental date: LocalDate
    - Return date: LocalDate
    - Actual return date: LocalDate
    - Car id: Long
    - User id: Long
4. Payment:
    - Status: Enum: PENDING | PAID
    - Type: Enum: PAYMENT | FINE
    - Rental id: Long
    - Session url: Url # URL for the payment session with a payment provider
    - Session id: String # ID of the payment session
    - Amount to pay: decimal (in $USD)  # calculated rental total price

### Controllers

1. Authentication Controller:
    - POST: /register - register a new user
    - POST: /login - get JWT tokens

2. Users Controller: Managing authentication and user registration
    - PUT: /users/{id}/role - update user role
    - GET: /users/me - get my profile info
    - PUT/PATCH: /users/me - update profile info

3. Cars Controller: Managing car inventory (CRUD for Cars)
    - POST: /cars - add a new car
    - GET: /cars - get a list of cars
    - GET: /cars/<id> - get car's detailed information
    - PUT/PATCH: /cars/<id> - update car (also manage inventory)
    - DELETE: /cars/<id> - delete car

4. Rentals Controller: Managing users' car rentals
    - POST: /rentals - add a new rental (decrease car inventory by 1)
    - GET: /rentals/?user_id=...&is_active=... - get rentals by user ID and whether the rental is still active or not
    - GET: /rentals/<id> - get specific rental
    - POST: /rentals/<id>/return - set actual return date (increase car inventory by 1)

5. Payments Controller (Stripe): Facilitates payments for car rentals through the platform. Interacts with Stripe API.
   Use stripe-java library.
    - GET: /payments - get payments
    - POST: /payments/ - create payment session
    - GET: /payments/success/ - check successful Stripe payments (Endpoint for stripe redirection)
    - GET: /payments/cancel/ - return payment paused message (Endpoint for stripe redirection)

6. Notifications Service (Telegram):
    - Notifications about new rentals created, overdue rentals, and successful payments
    - Other services interact with it to send notifications to car sharing service administrators.
    - Uses Telegram API, Telegram Chats, and Bots.


### How to run the project

To run this project locally, follow these steps:
1. Please make sure that you have all the required technologies installed before proceeding.
2. Clone this repository to your local machine.
3. Create a .env file and set up environment variables (e.g., database connection string, JWT secret).
4. Run Docker.
5. Run the command `docker compose up`.
6. For testing using Swagger, open your browser and enter the URL: `http://localhost:8088/api/swagger-ui/index.html#/`.
   Placeholder can be changes in properties.
   Then register a new user and test the app.
7. For testing using Postman, open the Postman web or desktop client and enter the URL `http://localhost:8088/api/{endpoint}`.
   Placeholder can be changes in properties.


We welcome contributions from the community. If you'd like to contribute to the project, please fork the repository,
make your changes, and submit a pull request.
