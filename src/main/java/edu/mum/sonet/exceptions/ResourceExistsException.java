package edu.mum.sonet.exceptions;

/**
 * Created by Jonathan on 12/12/2019.
 */

public class ResourceExistsException extends RuntimeException {

	public ResourceExistsException(String message) {
		super(message + " Already Exists");
	}
}
