package estudiando.microservicios.butacas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ButacaNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(ButacaNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String butacaNotFoundHandler(ButacaNotFoundException ex) {
		return ex.getMessage();
	}

}
