package estudiando.microservicios.peliculas;

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
	CommandLineRunner initDatabase(PeliculaRepository repository) {
		return args -> {
			repository.save(new Pelicula("Star wars", "nose", "asd", 190, "Ciencia ficcion", "buena", true, LocalDate.now(), LocalDate.now()));
			repository.save(new Pelicula("Indiana Jones", "nose", "asd", 163, "Aventura", "buena", true, LocalDate.now(), LocalDate.now()));
		};
	}
	
}
