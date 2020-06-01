# HauntedHouses
Haunted houses is a game where you search for specters and banish them from haunted houses.

## Installation
Clone the repository and compile the program using maven.
```
git clone https://github.com/433736/HauntedHouses
cd HauntedHouses/haunted-houses/   
mvn clean install
```

Use following commands to start the UI. The application is available at http://localhost:8080/pa165/
```
cd haunted-houses-mvc/ 
mvn cargo:run
```

Use following commands to start the REST API. Supported operations are available in haunted-houses-rest module readme.
```
cd haunted-houses-rest/ 
mvn tomcat7:run
```

## Login
  In its current state, the application does not support registration of new users. The list of available credentials is as follows:

##### 1.
  - Email: player1@email.com
  - Password: password
  - Role: user

##### 2.
  - Email: admin@email.com
  - Password: admin
  - Role: admin
