# International Space Station Spy App (ISS-Spy-App)
## intive Patronage 2019 - Wrocław - qualification task  
### by Tomasz Jarych
#### tjarych@gmail.com
* * * * 

RESTFull server application written in JAVA.

### Technology stack

Library | ver.
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

####Other technologies or libraries used in project:
* Maven
* Docker
* RestTemplate
* Spring Security (disabled CORS and CSRF)
* Spring Framework scheduling

#### Application consists of three independent layers:
##### 1. Persistent layer - H2 database where locations of ISS are storage
##### 2. Business logic layer:
 * services
 * mappers (eg. from entities to dtos)
 * scheduled service - fetch data from Open Notify server in background
 * Global Exception Handler
##### 3. View layer
* Rest controller

### Restpoints specification:
#### 1. */api/v1/iss/current* 
Shows current position of the ISS, output:
````json
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
or error standardized for all endpoints message
````json
{
  "message": "failed",
  "timestamp": UNIX_TIME_STAMP,
  "result": "ERROR_MESSAGE"
}
````

#### 2. */api/v1/iss/predict*
Show predicted overhead passes based on user's IP address
````json
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
####3. */api/v1/iss/speed*
Shows current the ISS moving speed. It's written that average speed 
is ca. 28.000 Km/h, but it can be different occasionally. 
     
**IMPORTANT!**

I have added little delay in response, approximately 3 seconds, for better measurement precision

````json
{
  "message": "success",
  "timestamp": UNIX_TIME_STAMP,
  "result": {
    "currentSpeed": SPEED_IN_KM_PER_HOUR
  }
}
````

####4. */api/v1/iss/distanceFromUser*
Shows current distance between User location (based on IP address) and point over which the ISS is.
````json
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

####4. */api/v1/iss/totalDistance*
Total mileage of the ISS from the start of the application.
````json
{
  "message": "success",
  "timestamp": UNIX_TIME_STAMP,
  "result": {
    "TotalDistance": TOTAL_DISTANCE_IN_KM
  }
}
````