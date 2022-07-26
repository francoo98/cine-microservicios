package estudiando.microservicios.salas.exceptions;

public class SalaNotFoundException extends RuntimeException {
	public SalaNotFoundException(long id) {
		super("Couldn't find a Sala with id " + id);
	}
}
