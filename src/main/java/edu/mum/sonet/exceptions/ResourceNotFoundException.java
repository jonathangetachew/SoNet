package edu.mum.sonet.exceptions;

/**
 * Created by Jonathan on 12/12/2019.
 */

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String message) {
		super(message + " Not Found");
	}
}
