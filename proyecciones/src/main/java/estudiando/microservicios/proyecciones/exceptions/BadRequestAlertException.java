package estudiando.microservicios.proyecciones.exceptions;

public class BadRequestAlertException extends RuntimeException {
	public BadRequestAlertException(String msg) {
	    super(msg);
	}
}
