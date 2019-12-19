package edu.mum.sonet.controllers;

import edu.mum.sonet.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan on 12/12/2019.
 */

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ UnhealthyContentDetectedException.class })
	public ResponseEntity<?> unhealthyContentDetectedHandler(UnhealthyContentDetectedException ex) {
		Map<Object, Object> response = new HashMap<>();
		response.put("status", HttpStatus.NOT_ACCEPTABLE.value());
		response.put("error", HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
		response.put("message", ex.getMessage());

		return ResponseEntity
				.status(HttpStatus.NOT_ACCEPTABLE)
				.body(response);
	}

	@ExceptionHandler({ ResourceNotFoundException.class })
	public ResponseEntity<?> resourceNotFoundHandler(ResourceNotFoundException ex) {
		Map<Object, Object> response = new HashMap<>();
		response.put("status", HttpStatus.NOT_FOUND.value());
		response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
		response.put("message", ex.getMessage());

		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(response);
	}

	@ExceptionHandler({ ResourceExistsException.class })
	public ResponseEntity<?> resourceExistsHandler(ResourceExistsException ex) {
		Map<Object, Object> response = new HashMap<>();
		response.put("status", HttpStatus.CONFLICT.value());
		response.put("error", HttpStatus.CONFLICT.getReasonPhrase());
		response.put("message", ex.getMessage());

		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(response);
	}

	@ExceptionHandler({ UnauthorizedUserException.class })
	public ResponseEntity<?> unauthorizedUserHandler(UnauthorizedUserException ex) {
		Map<Object, Object> responseMessage = new HashMap<>();
		responseMessage.put("status", HttpStatus.UNAUTHORIZED.value());
		responseMessage.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
		responseMessage.put("message", ex.getMessage());

		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.body(responseMessage);
	}

	@ExceptionHandler({ UserValidationException.class })
	public ResponseEntity<?> userValidationHandler(UserValidationException ex) {
		Map<Object, Object> responseMessage = new HashMap<>();
		responseMessage.put("status", HttpStatus.BAD_REQUEST.value());
		responseMessage.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
		responseMessage.put("message", ex.getMessage());

		return ResponseEntity
				.badRequest()
				.body(responseMessage);
	}
}
