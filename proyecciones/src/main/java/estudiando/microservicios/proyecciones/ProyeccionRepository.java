package estudiando.microservicios.proyecciones;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyeccionRepository extends JpaRepository<Proyeccion, Long> {

	List<Proyeccion> findAllByEstadoTrue();

	//List<Proyeccion> findProyeccionsByPeliculaId(Long id);

	List<Proyeccion> findProyeccionsByFechaFinGreaterThanEqualAndFechaInicioLessThanEqualAndEstadoTrue(LocalDate inicio,
																									   LocalDate fin);
	
	@Query("select p from Proyeccion p where curdate() between fechaInicio and fechaFin and estado = True")
	List<Proyeccion> findProyeccionsActiveToday();
	
	@Query("select p from Proyeccion p where fechaInicio <= ?1 and fechaFin >= ?1")
	List<Proyeccion> findAllByFechaInicioBeforeAndFechaFinAfterAndEstadoTrue(LocalDate hoy);
	/*@Query(nativeQuery = true, 
			value = "SELECT proyeccion.*, count(CASE WHEN butaca.estado = 'Vendida' AND butaca.fecha_de_venta BETWWEN :inicio, :fin THEN 1 END) as ButacasVendidas " +
					"FROM Proyeccion JOIN Butaca ON Proyeccion.id = Butaca.proyeccion_id " +
					"WHERE fecha_inicio <= :fin AND fecha_fin >= :inicio " +
					"GROUP BY proyeccion.id ORDER BY ButacasVendidas DESC LIMIT 5")
	Page<Proyeccion> masVendidas(@Param(value = "inicio") LocalDate inicio, @Param(value = "fin") LocalDate fin, Pageable pageable);*/

	/*
	@Query("select p from Proyeccion p where pelicula.id = ?1 and ?2 between fechaInicio and fechaFin and estado = True")
	Optional<List<Proyeccion>> findProyeccionsByPeliculaIdAndFechaInicioBeforeAndFechaFinAfter(Long pelicula_id,
																						 	   LocalDate fecha);
	*/

	/*List<Proyeccion> findProyeccionsByPeliculaAndFechaFinGreaterThanEqualAndFechaInicioLessThanEqualAndEstadoTrue(Pelicula p, 
			LocalDate inicio,
			LocalDate fin);*/
	
}
