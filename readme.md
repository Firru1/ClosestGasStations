# Spring Boot Application with Swagger Integration

## Overview
This application is a Spring Boot project that provides RESTful APIs for managing gas stations and their associated ZIP codes. It includes Swagger integration for API documentation.

## Prerequisites
- Java 11 or newer
- Maven or Gradle

## Running the Application
1. Clone the repository.
2. Navigate to the project directory.
3. Run `./mvnw spring-boot:run` or `./gradlew bootRun`.

## Accessing API Documentation
Once the application is running, access the Swagger UI at `http://localhost:8080/swagger-ui.html` to view and interact with the API documentation.

## Endpoints
- GET `/api/gas-stations/nearest?zipCode=<ZIP_CODE>`: Find nearest gas stations.
- POST `/api/gas-stations`: Add a new gas station.
- PUT `/api/gas-stations/{id}`: Update an existing gas station.
- DELETE `/api/gas-stations/{id}`: Delete an existing gas station.
- GET `/api/zip-codes`: Get all ZIP codes.

### Tables

#### `gas_stations`
- `id` (Primary Key): Unique identifier for each gas station.
- `name`: Name of the gas station.
- `latitude`: Latitude of the gas station's location.
- `longitude`: Longitude of the gas station's location.
- `zip_code_id` (Foreign Key): References the `zip_codes` table to associate the gas station with a specific zip code area.

#### `zip_codes`
- `id` (Primary Key): Unique identifier for each zip code entry.
- `zip_code`: The zip code value.
- `latitude`: Latitude of the center of the zip code area.
- `longitude`: Longitude of the center of the zip code area.

## Contributing
Contributions are welcome. Please feel free to submit a pull request.

## License
Aaqil Ghori
