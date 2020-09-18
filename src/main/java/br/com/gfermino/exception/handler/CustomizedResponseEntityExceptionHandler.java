package br.com.gfermino.exception.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.gfermino.exception.ResourceNotFoundException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	public final ResponseEntity handleAllExceptions(Exception ex, WebRequest request){
		return new ResponseEntity(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity handleBadRequestExceptions(Exception ex, WebRequest request){
		//ExceptionResponse exceptionResponse= new ExceptionResponse(ex.getMessage());
		return new ResponseEntity(ex.getMessage(),HttpStatus.NOT_FOUND);
	}
}
