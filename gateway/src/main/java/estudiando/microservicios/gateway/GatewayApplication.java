package estudiando.microservicios.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/get")
						.filters(f -> f.addRequestHeader("Hello", "World"))
						.uri("http://httpbin.org:80"))
				.route(p -> p
						.path("/api/peliculas")
						.uri("http://peliculas:8080/"))
				.route(p -> p
						.path("/api/peliculas/*")
						.uri("http://peliculas:8080/"))
				.route(p -> p
						.path("/api/salas")
						.uri("http://salas:8080"))
				.route(p -> p
						.path("/api/salas/*")
						.uri("http://salas:8080"))
				.route(p -> p
						.path("/api/proyeccions")
						.uri("http://proyecciones:8080"))
				.route(p -> p
						.path("/api/proyeccions/*")
						.uri("http://proyecciones:8080"))
				.route(p -> p
						.path("/api/butacas")
						.uri("http://butacas:8080"))
				.route(p -> p
						.path("/api/butacas/*")
						.uri("http://butacas:8080"))
				.build();
	}
}
