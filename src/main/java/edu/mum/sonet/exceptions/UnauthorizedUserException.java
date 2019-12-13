package edu.mum.sonet.exceptions;

/**
 * Created by Jonathan on 12/12/2019.
 */

public class UnauthorizedUserException extends RuntimeException {

	public UnauthorizedUserException(String message) {
		super(message);
	}
}
