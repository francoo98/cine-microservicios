package estudiando.microservicios.peliculas.exceptions;

public class PeliculaNotFoundException extends RuntimeException {

	public PeliculaNotFoundException(Long id) {
	    super("Could not find pelicula " + id);
	}
	
}
