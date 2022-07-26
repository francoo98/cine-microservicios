package estudiando.microservicios.proyecciones.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ProyeccionNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(ProyeccionNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String proyeccionNotFoundHandler(ProyeccionNotFoundException ex) {
    return ex.getMessage();
  }
}
