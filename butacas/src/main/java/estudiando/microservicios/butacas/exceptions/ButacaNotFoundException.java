package estudiando.microservicios.butacas.exceptions;

public class ButacaNotFoundException extends RuntimeException {

	public ButacaNotFoundException(Long id) {
		super("Couldn't find butaca " + id);
	}
	
}
