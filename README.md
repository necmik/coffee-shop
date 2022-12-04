## Coffee Shop

Coffee Shop is a backend for online coffee place startup business project.Its functions are :
* Users can place drinks with toppings orders
* Each order my have multiple coffees and each coffee may have multiple toppings.
* Admins can create/update/delete drinks/toppings (AuthN/AuthZ mechanism not implemented, so these APIs are open)
* Admins have access to the most used toppoings.

The project was developed with Spring Boot framework and H2 in-memory database for testing/POC purpose. 

### Minimum Requirements to build/deploy
* JDK 17+
* IDE like Eclipse, IntelliJ or Spring Tool Suite(STS) with Lombok
* Git
* Postman like HTTP client tool to run REST services. 

### Installation Steps

* Clone the project from https://github.com/necmik/coffee-shop. 
* Check application.properties for datasource connection settings
* Import the Maven project to your favourite IDE
* Run the project via "Run As"->"Spring Boot App" if you are using Spring Tool Suite or select "Run As"-> "Maven Build.." and enter spring-boot:run in the goals for Eclipse.
* Because it is a spring boot project, it already has Tomcat embedded web server. The default port is 8080 if you haven't override in application.properties.
 
### Services

The project contains 3 basic Rest API:

* /api/coffees : CRUD operations for coffees.
  
* /api/toppings : CRUD and most used operations for toppings.

* /api/orders : CRUD operations for orders.
 
 