# User Authentication Service

## Overview
A Spring Boot-based authentication service that provides secure user authentication and authorization using JWT tokens. This service is part of the INSERM platform infrastructure.

## Features
- User registration
- Secure authentication
- JWT token management
- Role-based authorization

## Technical Stack
- Java 17
- Spring Boot 3.x
- Spring Security
- MySQL
- JWT (JSON Web Tokens)
- Maven

## Prerequisites
- JDK 17+
- Maven 3.6+
- MySQL database

## Setup and Installation

1. Clone the repository
    bash
    git clone [repository-url]

2. Configure database properties in `application.properties`
    properties:
    spring.datasource.url=jdbc:mysql://localhost:3306/auth_db
    spring.datasource.username=username
    spring.datasource.password=password

3. Build the project

    bash
    mvn clean install

4. Run the application

    bash
    mvn spring-boot:run

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Authenticate user
- `POST /api/auth/validate` - Validate JWT token

## Testing
Run the test suite:

    bash
    mvn test

## Security
- JWT-based authentication
- Password encryption
- Role-based access control

## Support
For support queries, contact: valentin.javaux@ext.inserm.fr

