package estudiando.microservicios.peliculas.exceptions;

public class BadRequestAlertException extends RuntimeException {

	public BadRequestAlertException(String msg) {
	    super(msg);
	}
	
}
