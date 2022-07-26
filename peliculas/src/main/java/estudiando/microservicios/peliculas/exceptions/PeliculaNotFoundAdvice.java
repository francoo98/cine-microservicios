package estudiando.microservicios.peliculas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class PeliculaNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(PeliculaNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String employeeNotFoundHandler(PeliculaNotFoundException ex) {
    return ex.getMessage();
  }
}
