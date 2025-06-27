# EasyShop - E-commerce Web Application

## Project Description

EasyShop is a full-stack e-commerce web application built with Java, Spring Boot, and a modern front-end. It provides a platform for users to browse products, add them to a shopping cart, and complete the checkout process. The application features a secure authentication system, a product catalog with filtering capabilities, and a user profile management system.

This project was developed as part of the YearUp Learn To Code Academy (LTCA) program (Spring 2025 cohort), with
curriculum provided by [Pluralsight](https://www.pluralsight.com/).

Guidance provided by Pluralsight instructor [Craig McKeachie](https://www.linkedin.com/in/craigmckeachie/).

## Features

* **User Authentication:** Secure user registration and login functionality using JWT (JSON Web Tokens).
* **Product Catalog:** Browse a catalog of products with the ability to filter by category, price range, and color.
* **Shopping Cart:** Add products to a shopping cart, update quantities, and view the cart total.
* **Checkout Process:** Place an order and have it saved to the database.
* **User Profile Management:** Users can view and update their profile information.
* **Admin Functionality:** Role-based access control, with admin users having the ability to add, update, and delete products and categories.
* **RESTful API:** A well-defined RESTful API for communication between the front-end and back-end.

## Technologies Used

### Back-End

* **Java:** The primary programming language for the back-end.
* **Spring Boot:** Framework for creating stand-alone, production-grade Spring-based Applications.
* **Spring Security:** For handling authentication and authorization.
* **Spring Data JPA:** For data persistence and interaction with the database.
* **JWT (JSON Web Token):** For stateless authentication.
* **MySQL:** The relational database used to store application data.
* **Flyway:** For database migration and version control.
* **Maven:** For dependency management and build automation.
* **Lombok:** To reduce boilerplate code for model objects.
* **MapStruct:** For mapping between different object models.

## Project Structure

The project is divided into two main parts: the back-end Spring Boot application and the front-end web application.

### Back-End Structure

```code
capstone-starter/
└── src
├── main
│   ├── java
│   │   └── org
│   │       └── yearup
│   │           ├── controllers
│   │           ├── data
│   │           ├── mapper
│   │           ├── models
│   │           ├── security
│   │           └── service
│   └── resources
│       ├── db
│       │   └── migration
│       └── application.properties
```

* **`controllers`**: Contains the REST controllers that handle incoming HTTP requests.
* **`data`**: Contains the Spring Data JPA repositories for database interaction.
* **`mapper`**: Contains MapStruct mappers for converting between DTOs and entities.
* **`models`**: Contains the JPA entity classes that represent the database tables.
* **`security`**: Contains the security configuration, JWT implementation, and user details service.
* **`service`**: Contains the business logic of the application.
* **`resources/db/migration`**: Contains the Flyway SQL scripts for database schema creation and migration.

---

## API Endpoints

The following are the main API endpoints provided by the back-end:

#### Authentication

* `POST /login`: Authenticate a user and receive a JWT token.
* `POST /register`: Register a new user.

#### Products

* `GET /products`: Search for products with optional filters.
* `GET /products/{id}`: Get a product by its ID.
* `POST /products`: Add a new product (*Admin only*).
* `PUT /products/{id}`: Update a product (*Admin only*).
* `DELETE /products/{id}`: Delete a product (*Admin only*).

#### Categories

* `GET /categories`: Get all categories.
* `GET /categories/{id}`: Get a category by its ID.
* `GET /categories/{categoryId}/products`: Get all products in a specific category.
* `POST /categories`: Add a new category (*Admin only*).
* `PUT /categories/{id}`: Update a category (*Admin only*).
* `DELETE /categories/{id}`: Delete a category (*Admin only*).

#### Shopping Cart

* `GET /cart`: Get the current user's shopping cart.
* `POST /cart/products/{productId}`: Add a product to the cart.
* `PATCH /cart/products/{productId}`: Update the quantity of a product in the cart.
* `DELETE /cart`: Clear all items from the cart.

#### Orders

* `POST /orders`: Place an order from the items in the shopping cart.

#### Profile

* `GET /profile`: Get the current user's profile.
* `PUT /profile`: Update the current user's profile.

## Database Schema

The database schema is managed by **Flyway** and is defined in the SQL migration files located in `src/main/resources/db/migration`. The main tables are:

* **`users`**: Stores user authentication information.
* **`profiles`**: Stores user profile information.
* **`categories`**: Stores product categories.
* **`products`**: Stores product information.
* **`orders`**: Stores order information.
* **`order_line_items`**: Stores the individual items within an order.
* **`shopping_cart`**: Stores the items in a user's shopping cart.

*The schema is automatically created and migrated when the application starts up.*

## How to Run the Application

To run the application, you need to have **Java**, **Maven**, and **MySQL** installed on your machine.

1.  **Database Setup:**
    * Create a MySQL database named `easy_shop`.
    * Update the `datasource.url`, `datasource.username`, and `datasource.password` properties in `src/main/resources/application.properties` to match your MySQL configuration.

2.  **Navigate to Project Root:**
    ```bash
    cd path/to/capstone-starter
    ```

3.  **Run the Application:**
    Use Maven to run the application:
    ```bash
    mvn spring-boot:run
    ```

4.  **Access the Application:**
    Once the application is running, you can access the front-end by opening the `capstone-client-web-application/index.html` file in your web browser.

---

## How to Run Tests

This project uses **JUnit 5** for unit testing. To run the tests, navigate to the project's root directory (`capstone-starter`) and execute the following Maven command:

> ```bash
> mvn test
> ```

This command will compile the test source code, run all tests defined in the `src/test/java` directory, and report the results.

---