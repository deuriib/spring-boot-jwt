# Spring Boot JWT Authentication

This project is a Spring Boot application that demonstrates JSON Web Token (JWT) authentication for securing APIs.

## Prerequisites

Before you begin, make sure you have the following installed:

- Java 17 Development Kit (JDK)
- Maven
- Your preferred Integrated Development Environment (IDE)

## Getting Started

Follow these steps to set up and run the project:

1. Clone the repository:

    ```bash
    git clone https://github.com/deuriib/spring-boot-jwt.git
    ```

2. Navigate to the project directory:

    ```bash
    cd spring-boot-jwt
    ```

3. Build the project with Maven:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    java -jar target/spring-boot-jwt-1.0.0.jar
    ```

The application will start, and you can access it at [http://localhost:8080](http://localhost:8080).

## Usage

This project provides an authentication system using JWT. Here's how you can use it:

- Create a user by making a POST request to `/api/auth/register` with valid credentials.
- Obtain a JWT token by making a POST request to `/api/auth/login` with valid credentials.
- Include the obtained token in the Authorization header for subsequent protected API requests.

## Configuration

You can configure the application by modifying the `application.properties` file.