Requirements
-

The project was tested on a system with the following dependencies:

#### Java 12.0.1
#### Apache Maven 3.6.0

*Note:* the project successfully runs with lower versions than those specified above.

Setup
-

The following guide to provision a development environment assumes Linux as the operating system. 
If you use OSX or Windows, you will need to adapt accordingly.


1. Clone the repository of the rides platform task and change directory to RidesPlatform

    ```bash
    git clone https://github.com/LubomirHristov/RidesPlatform.git
    cd RidesPlatform/
    ```

2. Build the project with Maven

    ```bash
    mvn clean
    mvn compile
    mvn package
    mvn install assembly:single
    ```
    
Part 1
-

### Console application to print the search results for Dave's Taxis

```bash
java -cp target/RidesPlatform-1.0-SNAPSHOT-jar-with-dependencies.jar console.ConsoleSearchEngine <pickup> <dropoff> <passengers> <supplier>
```
*Example:*
```bash
java -cp target/RidesPlatform-1.0-SNAPSHOT-jar-with-dependencies.jar console.ConsoleSearchEngine 51.470020,-0.454295 51.00000,112.0000 4 dave
```

*Note:* the first 3 arguments are required by the application. 
If a `supplier` argument is present then the engine makes a search for that supplier only, 
otherwise it calls the API of all suppliers.

`pickup` - pickup location for the journey, *e.g. 51.470020,-0.454295*  
`dropoff` - drop off location for the journey, *e.g. 51.00000,112.0000*  
`passengers` - the number of passengers  
`supplier` - the name of the supplier
    
### Console application to print the search results for all APIs

```bash
java -cp target/RidesPlatform-1.0-SNAPSHOT-jar-with-dependencies.jar console.ConsoleSearchEngine <pickup> <dropoff> <passengers>
```

*Example:*

```bash
java -cp target/RidesPlatform-1.0-SNAPSHOT-jar-with-dependencies.jar console.ConsoleSearchEngine 51.470020,-0.454295 51.00000,112.0000 4
```

The 3 required arguments are the same as in Part 1A and the supplier argument is missing indicating that the engine 
should call the API of all suppliers.
    
    
Part 2
-

1. To run Part 2 use the following command in the root directory

    ```bash
    mvn spring-boot:run
    ```
    
    The server should start running on `localhost:8080` and the endpoint for the output of the search engine is `/json`.
    
    To make a search you can use `curl`
    
    Part 1A
    ```bash
    curl -X GET 'http://localhost:8080/json?pickup=<pickup>&dropoff=<dropoff>&passengers=<passengers>&supplier=<supplier>'
    ``` 
    
    *Example:* 
    ```bash
    curl -X GET 'http://localhost:8080/json?pickup=51.470020,-0.454295&dropoff=51.00000,112.0000&passengers=4&supplier=dave'
    ```
    
    Part 1B
    ```bash
    curl -X GET 'http://localhost:8080/json?pickup=<pickup>&dropoff=<dropoff>&passengers=<passengers>'
    ```
    
    *Example:* 
    ```bash
    curl -X GET 'http://localhost:8080/json?pickup=51.470020,-0.454295&dropoff=51.00000,112.0000&passengers=4'
    ```
    