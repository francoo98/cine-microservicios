package estudiando.microservicios.salas.exceptions;

public class BadRequestAlertException extends RuntimeException {

	public BadRequestAlertException(String msg) {
	    super(msg);
	}
	
}
