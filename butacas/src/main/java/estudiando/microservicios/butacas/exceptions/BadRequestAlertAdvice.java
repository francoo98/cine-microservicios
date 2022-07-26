package estudiando.microservicios.butacas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BadRequestAlertAdvice {
	
	@ExceptionHandler(BadRequestAlertException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String BadRequestAlertHandler(BadRequestAlertException ex) {
	    return ex.getMessage();
	  }
}

