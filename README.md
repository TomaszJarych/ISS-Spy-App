# International Space Station Spy App (ISS-Spy-App)
## intive Patronage 2019 - Wrocław - qualification task  
### by Tomasz Jarych
#### tjarych@gmail.com
* * * * 

RESTFull server application written in JAVA.

### Technology stack

Library | ver
--------|---------
Java    | 1.8
Spring-boot | 2.1.3
Spring Framework | 5.1.5
Spring Data JPA | 2.1.5
Jackson Project | 2.9.8
H2 Database | 1.4.197
Project Lombok | 1.18.6
MapStruct | 1.3.0. Final
JUnit | 5.3.2
Mockito | 2.25.0
Payara Micro | 5.183

#### Other technologies or libraries used in project:
* Maven
* Docker
* RestTemplate
* Spring Framework scheduling

#### Application consists of three independent layers:
##### 1. Persistent layer - H2 database where locations of the ISS are storage
##### 2. Business logic layer:
 * services
 * mappers (eg. from entities to dtos)
 * scheduled service - fetch data from Open Notify server in background - **Once per every minute**
 * Global Exception Handler
##### 3. View layer
* Rest controller

### Endpoints specification:
#### 1. */api/v1/iss/current* 
Shows current position of the ISS, output:
````
{
  "message": "success", 
  "timestamp": UNIX_TIME_STAMP,
  "result": {
    "time": 1552761209,
    "latitude": CURRENT_LATITUDE,
    "longitude": CURRENT_LONGITUDE
  }
}
````
or error standardized message for all endpoints
````
{
  "message": "failed",
  "timestamp": UNIX_TIME_STAMP,
  "result": "ERROR_MESSAGE"
}
````

#### 2. */api/v1/iss/predict*
Show predicted overhead passes based on user's IP address
````
{
  "message": "success",
  "timestamp": UNIX_TIME_STAMP,
  "result": {
    "passesNumber": NUMBER_OF_PREDICTED_PASSES,
    "latitude": USERS_LATITUDE,
    "longitude": USERS_LONGITUDE,
    "passesData": [
      {
        "duration": DURATION_IN_SECONDS,
        "risetime": TIME_IN_LOCALIZADE_ZONE_ID_FOR_POLAND
      }
    ]
  }
}
```` 
**IMPORTANT!**

This feature only works properly if user's IP address is different than server. When user's IP address is pointed as *localhost* 
or equivalent, location server returns invalid data and application will throw an exception. As a result request 
answer will be reported as *failed*.

#### 3. */api/v1/iss/speed*
Shows current the ISS moving speed based on two separated measurement. It's written that average speed 
is ca. 28.000 Km/h, but it can be different occasionally. 
     
**IMPORTANT!**

I have added little delay in response, approximately 3 seconds, for better measurement precision

````
{
  "message": "success",
  "timestamp": UNIX_TIME_STAMP,
  "result": {
    "currentSpeed": SPEED_IN_KM_PER_HOUR
  }
}
````

#### 4. */api/v1/iss/distanceFromUser*
Shows current distance between User location (based on IP address) and point over which the ISS is.
````
{
  "message": "success",
  "timestamp": UNIX_TIME_STAMP,
  "result": {
    "locationName": CITYNAME,
    "distance": DISTANCE_IN_KM,
    "time": UNIX_TIME_STAMP,
    "country": COUNTRY,
    "latitude": USERS_LATITUDE,
    "longitude": USERS_LONGITUDE
  }
}
````

**IMPORTANT!**

This feature only works properly if user's IP address is different than server. When user's IP address is pointed as *localhost* 
or equivalent, location server returns invalid data and application will throw an exception. As a result request 
answer will be reported as *failed*. 

#### 5. */api/v1/iss/totalDistance*
Total mileage of the ISS from the start of the application.
````
{
  "message": "success",
  "timestamp": UNIX_TIME_STAMP,
  "result": {
    "TotalDistance": TOTAL_DISTANCE_IN_KM
  }
}
````

#### 5. */api/v1/iss/distanceFromLocation?lat=<LAT_DEGREES>&latDir=<LAT_DIRECTION>&lon=<LON_DEGREES>&lonDir=<LON_DIRECTION>*
Shows current distance between User location (based on given coordinates) and point over which the ISS is.

Input | Description| Query string| Range | VALUES
--------|---------|-----|---------|----------|
LAT_DEGREES| The latitude of the place to predict passes | *lat* | 0-90| DECIMAL 
LAT_DIRECTION | Specifies the north–south position | *latDir* | |NORTH or SOUTH
LON_DEGREES | The longitude of the place to predict passes| *lon* | 0-180| DECIMAL
LON_DIRECTION|Specifies the east–west position of a point| *lonDir*| | EAST or WEST


**IMPORTANT!**
All above data are required!
````
{
  "message": "success",
  "timestamp": UNIX_TIME_STAMP,
  "result": {
    "distance": DISTANCE_IN_KM,
    "time": UNIX_TIME_STAMP,
    "latitude": USERS_LATITUDE,
    "longitude": USERS_LONGITUDE
  }
}
````

#### 6. */api/v1/iss/predictFromCoordinates?lat=<LAT_DEGREES>&latDir=<LAT_DIRECTION>&lon=<LON_DEGREES>&lonDir=<LON_DIRECTION>*
Show predicted overhead passes based on based on given coordinates

Input | Description| Query string| Range | VALUES
--------|---------|-----|---------|----------|
LAT_DEGREES| The latitude of the place to predict passes | *lat* | 0-80| DECIMAL 
LAT_DIRECTION | Specifies the north–south position | *latDir* | |NORTH or SOUTH
LON_DEGREES | The longitude of the place to predict passes| *lon* | 0-180| DECIMAL
LON_DIRECTION|Specifies the east–west position of a point| *lonDir*| | EAST or WEST


**IMPORTANT!**
All above data are required!
````
{
  "message": "success",
  "timestamp": UNIX_TIME_STAMP,
  "result": {
    "passesNumber": NUMBER_OF_PREDICTED_PASSES,
    "latitude": USERS_LATITUDE,
    "longitude": USERS_LONGITUDE,
    "passesData": [
      {
        "duration": DURATION_IN_SECONDS,
        "risetime": TIME_IN_LOCALIZADE_ZONE_ID_FOR_POLAND
      }
    ]
  }
}
````
### Final notes

One of the task condition's is that the applications must easily compile in standard environment.
I've decided not to use external database management system (eg. MySQL or Postgres), 
instead I've implemented H2 in-memory database. Major disadvantage of this solution is fact, that all stored data are 
temporary and will disappear when application is shut down. H2 is easily replaceable for external database server. 
There is no need to change code, only override applications.properties file.

Application could be run as a standalone .jar environment. When profile **payara-microserver-bundle** is active, *maven package* 
execution will prepare also FatJar file, whit embedded Payara Micro server. I've prepared also *Dockerfile* and *docker-compose.yml* 
files using OpenJDK environment for this application.  

Through application development stage I've have try to follow the SOLID and Clean Code rules.
Every application layer is independent and all required dependencies are delivered through Dependency Injection.
All methods are designed with Single Responsibility Principle, service layer is described by Interfaces, so future improvements 
can be easily applied.

I've attached also INSOMNIA file with endpoints testing environment. 
**Testing endpoints which depends on external IP address location server will throw exception
if user's IP address is specified as *localhost*. Fot this reason test this endpoints with hardcoded IP value or use endpoints based on given coordinates.
Underneath logic is the same and output will be equivalent.**

    






