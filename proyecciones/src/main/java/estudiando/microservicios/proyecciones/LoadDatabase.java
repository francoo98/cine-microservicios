package estudiando.microservicios.proyecciones;

import java.time.Instant;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	@Bean
	CommandLineRunner initDatabase(ProyeccionRepository repository) {
		return args -> {
			repository.save(new Proyeccion(LocalDate.now(), LocalDate.now(), Instant.now(), true, 1L, 1L));
			repository.save(new Proyeccion(LocalDate.now(), LocalDate.now(), Instant.now(), true, 2L, 2L));
		};
	}
	
}
