package estudiando.microservicios.proyecciones.exceptions;

public class ProyeccionNotFoundException extends RuntimeException {

	public ProyeccionNotFoundException(Long id) {
	    super("Could not find proyeccion " + id);
	}
	
}
