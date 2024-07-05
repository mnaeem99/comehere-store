# Store Management System

This project is a Store Management System with a frontend built using Angular and a backend developed using Java Spring Boot with Maven. The system includes features for user management, product management, order processing, customer management, and inventory tracking.

## Table of Contents
- [Features](#features)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [License](#license)

## Features
- User authentication and authorization
- Product management
- Customer management
- Order processing and tracking
- Inventory management
- Supplier management
- Comprehensive reporting and analytics

## Architecture
- **Frontend**: Angular
- **Backend**: Java Spring Boot
- **Database**: PostgreSQL

## Prerequisites
- Node.js and npm
- Angular CLI
- Java 11 or later
- Maven
- PostgreSQL

## Installation

### Backend
1. Clone the repository:
    ```bash
    git clone https://github.com/mnaeem99/comehere-store.git
    cd comehere
    ```

2. Configure the database:
    - Create a PostgreSQL database named `store`.
    - Update `src/main/resources/application.properties` with your PostgreSQL configuration:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/store
        spring.datasource.username=yourusername
        spring.datasource.password=yourpassword
        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
        ```

3. Build and run the backend:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

### Frontend
1. Navigate to the frontend directory:
    ```bash
    cd ../comehereClient
    ```

2. Install dependencies:
    ```bash
    npm install
    ```

3. Run the frontend:
    ```bash
    ng serve
    ```

    The frontend will be available at `http://localhost:4200`.

## Running the Application
- Ensure PostgreSQL is running and the database is configured correctly.
- Start the backend server using Maven.
- Start the Angular frontend using Angular CLI.



## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
