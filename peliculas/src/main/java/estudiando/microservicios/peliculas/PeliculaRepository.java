package estudiando.microservicios.peliculas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long>{
	
	List<Pelicula> findAllByFechaFinGreaterThanEqualAndFechaInicioLessThanEqualAndEstadoTrue(LocalDate inicio, LocalDate fin);
	
}
