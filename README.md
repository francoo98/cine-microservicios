# cine-microservicios
A microservice architecture to manage a cinema.
Each one of the microservices has the purpose of managing its respective domain class.
The containers to run are specified in the [docker-compose.yml](https://github.com/francoo98/cine-microservicios/blob/main/docker-compose.yml) file, it runs each microservice in a separate container.
# Microservices
## `gateway`
It was created using spring gateway with the purpose of receving HTTP requests from the clients and routing them to the correct microservices.
The routes are defined [here](https://github.com/francoo98/cine-microservicios/blob/main/gateway/src/main/java/estudiando/microservicios/gateway/GatewayApplication.java).
## `peliculas`
Domain class: [Pelicula](https://github.com/francoo98/cine-microservicios/blob/main/peliculas/src/main/java/estudiando/microservicios/peliculas/Pelicula.java). It represents a movie.
#### Endpoints
1. GET/POST/PUT `/api/peliculas`
2. GET/DELETE `/api/peliculas/{id}`
## `proyecciones`
Domain class: [Proyeccion](https://github.com/francoo98/cine-microservicios/blob/main/proyecciones/src/main/java/estudiando/microservicios/proyecciones/Proyeccion.java). It represents a movie proyection in a theater.
#### Endpoints
1. GET/POST/PUT `/api/proyeccions`
2. GET/DELETE `/api/proyeccions/{id}`
3. GET `/api/proyeccions/hoy` Returns a list of `Proyeccion` that are active the day the request was sent.
## `salas`
Domain class: [Sala](https://github.com/francoo98/cine-microservicios/blob/main/salas/src/main/java/estudiando/microservicios/salas/Sala.java). It represents a theater in wich `Proyeccion`s can be played.
#### Endpoints
1. GET/POST/PUT `/api/salas`
2. GET/DELETE `/api/salas/{id}`
## `butacas`
Domain class: [Butaca](https://github.com/francoo98/cine-microservicios/blob/main/butacas/src/main/java/estudiando/microservicios/butacas/Butaca.java). It represents a seat in a `Proyeccion`.
#### Endpoints
1. GET/POST/PUT `/api/butacas`
2. GET `/api/butacas/{id}`
