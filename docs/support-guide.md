# User Authentication Service - Support Documentation

## Overview
This service provides user authentication and authorization functionality using JWT (JSON Web Tokens). 
It handles user registration, login, and token validation operations for the INSERM platform.

## System Requirements
- Java 17 or higher
- Maven 3.6 or higher
- Spring Boot 3.x
- PostgreSQL database

## Key Components

### 1. Authentication Endpoints

#### Register User
- **Endpoint**: `POST /api/auth/register`
- **Purpose**: Creates new user accounts
- **Request Body**: 
    json
    {
        "username": "string",
        "password": "string",
        "roles": ["string"]
    }
- **Response**: Returns user details on successful registration

#### Login
- **Endpoint**: `POST /api/auth/login`
- **Purpose**: Authenticates users and provides JWT token
- **Request Body**:
    json
    {
        "username": "string",
        "password": "string"
    }
- **Response**: Returns JWT token on successful authentication

### Validate Token
- **Endpoint**: `POST /api/auth/validate`
- **Purpose**: Validates JWT token
- **Request Body**:
    json
    {
        "token": "string"
    }
- **Response**: Returns user details on successful validation

### 2. Error Handling

#### Common HTTP Status Codes
- `200 OK`: Operation successful
- `400 BAD_REQUEST`: Invalid input data
- `401 UNAUTHORIZED`: Authentication failed
- `403 FORBIDDEN`: Insufficient permissions
- `500 INTERNAL_SERVER_ERROR`: Server-side error

#### Common Error Scenarios
1. Invalid Credentials
   - Check username and password
   - Verify user exists in database

2. Token Issues
   - Verify token format (Bearer token)
   - Check token expiration
   - Validate token signature

### 3. Configuration

#### Application Properties
Key settings in `application.properties`:
    spring.datasource.url=jdbc:mysql://localhost:3306/auth_db
    spring.datasource.username=${DB_USERNAME}
    spring.datasource.password=${DB_PASSWORD}
    jwt.secret=${JWT_SECRET}

### 4. Monitoring

#### Key Areas to Monitor
- Failed login attempts
- Token validation failures
- Database connection issues
- API response times

#### Logs
Important log patterns:
- Authentication failures
- Database errors
- JWT-related issues

### 5. Troubleshooting Guide

1. User Cannot Login
   - Verify username exists
   - Check password hash
   - Confirm database connection
   - Review authentication logs

2. Token Validation Fails
   - Check token format
   - Verify JWT secret
   - Confirm token hasn't expired

3. Database Issues
   - Verify connection string
   - Check credentials
   - Confirm PostgreSQL service status

### 6. Support Contacts

For technical issues:
- Primary: tech.support@inserm.fr
- Emergency: [emergency contact number]

## Maintenance Procedures

### Service Restart Procedure
1. Stop application gracefully
2. Verify no active sessions
3. Start application
4. Verify health checks

### Health Checks
- Database connectivity
- JWT service status
- API endpoint availability