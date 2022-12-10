## Coffee Shop

Coffee Shop is a backend for online coffee place startup business project.

### Core Functions
* Users can place drinks with toppings orders
* Each order my have multiple coffees and each coffee may have multiple toppings.
* Admins can create/update/delete drinks/toppings (AuthN/AuthZ mechanism not implemented, so these APIs are open)
* Admins have access to the most used toppoings.
* Admins can get top toppings with parameter

### Out of Scopes
* AuthN/AuthZ implemention for admin services
* UI design

### Minimum Requirements to build/deploy
The project was developed with Spring Boot framework and H2 in-memory database for testing/POC purposes. Minimum requirements for setup are:
* JDK 17+
* An IDE like Eclipse, IntelliJ or Spring Tool Suite(STS) with Lombok
* Git
* An HTTP client tool like Postman to test REST services. 

### Installation Steps

* Clone the project from https://github.com/necmik/coffee-shop. 
* Check application.properties for datasource connection settings
* Import the Maven project to your favourite IDE
* Run the project via "Run As"->"Spring Boot App" if you are using Spring Tool Suite or select "Run As"-> "Maven Build.." and enter spring-boot:run in the goals for Eclipse.
* Because it is a spring boot project, it already has Tomcat embedded web server. The default port is 8080 if you haven't override in application.properties.
 
### Services

The project contains 3 basic Rest API set:

* CRUD operations for coffees.
  - Get all coffees : GET localhost:8080/coffeeshop/coffees
  - Get coffee by id: GET localhost:8080/coffeeshop/coffees/{id}
  - Create a new coffee: POST localhost:8080/coffeeshop/coffees
  - Update a coffee: PUT localhost:8080/coffeeshop/coffees/{id}
  - Delete a coffee: DELETE localhost:8080/coffeeshop/coffees/{id}
  
* CRUD operations for toppings:
  - Get all toppings: GET localhost:8080/coffeeshop/toppings
  - Get topping by id: GET localhost:8080/coffeeshop/toppings/{id}
  - Create a new topping: POST localhost:8080/coffeeshop/toppings
  - Update a topping: PUT localhost:8080/coffeeshop/toppings/{id}
  - Delete a topping: DELETE localhost:8080/coffeeshop/toppings/{id}
  - Listing top N toppings by order count : GET localhost:8080/coffeeshop/toppings/getTopNTopping/{limit}

* CRUD operations for orders:
  - 
 