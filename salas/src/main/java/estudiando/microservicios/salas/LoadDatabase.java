package estudiando.microservicios.salas;

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
	CommandLineRunner initDatabase(SalaRepository repository) {
		return args -> {
			repository.save(new Sala("Sala 1", EstadosSala.Deshabilitada, 7, 8));
			repository.save(new Sala("Sala azul", EstadosSala.Habilitada, 8, 8));
		};
	}
	
}
