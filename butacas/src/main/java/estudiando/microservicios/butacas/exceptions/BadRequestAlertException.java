package estudiando.microservicios.butacas.exceptions;

public class BadRequestAlertException extends RuntimeException {
	public BadRequestAlertException(String msg) {
	    super(msg);
	}
}
