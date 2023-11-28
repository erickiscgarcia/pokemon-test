
# Project Name

- Version: 0.0.1
- Spring Boot Version: 2.5.3
- Java Version: 11

## Description
This project is an API that consumes an external API to get information about pokemons. It saves the retrieved information in a database and provides endpoints to create, list, and delete pokemons based on their ID or name.

## Features
- Spring Security for authentication and authorization
- Swagger for API documentation

## Prerequisites
- Java 11
- Maven
- Database (e.g., MySQL, PostgreSQL)

## Getting Started
1. Clone the repository.
2. Configure the database connection in the `application.properties` file.
3. Run the application using Maven: `mvn spring-boot:run`.
4. Open your browser and go to `http://localhost:8080/swagger-ui.html` to access the Swagger documentation.

## Endpoints

### Create Pokemon
POST /api/pokemons
Create a new pokemon.

### List All Pokemons

GET /api/pokemons
Get a list of all pokemons.

### Delete Pokemon by ID
DELETE /api/pokemons/{id}
Delete a pokemon by its ID.

### Delete Pokemon by Name
DELETE /api/pokemons?name={name}
Delete a pokemon by its name.

## Security
This project uses Spring Security for authentication and authorization. Access to certain endpoints may require authentication.

## Documentation
The API documentation is generated using Swagger. You can access the documentation at `http://localhost:8080/swagger-ui.html` after running the application.

## Postman Collection
A Postman collection has been added to the project, allowing easy integration and testing of the API endpoints. It is available in the resources directory of the Spring Boot Java project.

### Getting Started
To utilize the Postman collection and test the API endpoints, please follow the steps below:

- Ensure that you have Postman installed on your local machine. If not, you can download and install it from the official Postman website.
- Open Postman and import the collection by clicking on the "Import" button located at the top left corner of the Postman application.
- In the "Import" dialog, select the option to import from a "Link".
- Enter the following link to import the collection: link-to-postman-collection
- After the import is complete, you will see the collection named "Project API" in the left sidebar of Postman.

### Collection Structure
- The Postman collection is organized into different folders representing different API endpoints. Each request within the folder corresponds to a specific functionality of the API.

### To use the Postman collection, follow these steps:
- Expand the desired folder.
- Click on a specific request to get the token.
- The token will be automatically assigned in the environment for use by other requests
- Click on the "Send" button to send the request to the API endpoint.
- Once the response is received, you can view the response data, headers, and status code within Postman.

Note: Make sure the Spring Boot application is running locally or deployed to an accessible server before executing the requests in Postman.

## Contributors
- [Erick García](https://github.com/erickiscgarcia)

## License
This project is licensed under the [ADDV License](LICENSE).