package edu.mum.sonet.services;

/**
 * Created by Jonathan on 12/12/2019.
 */

public interface EmailService {
	void sendEmail(String userEmail, String subject, String bodyText);
}
