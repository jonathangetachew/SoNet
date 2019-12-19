package edu.mum.sonet.services.impl;

import edu.mum.sonet.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by Jonathan on 12/12/2019.
 */

@Service
public class EmailServiceImpl implements EmailService {

	@Value("{spring.mail.username}") // Get sender email from properties file / environment variable
	private String senderEmail;

	private final JavaMailSender javaMailSender;

	public EmailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void sendEmail(String userEmail, String subject, String bodyText) throws MailException {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(userEmail);
		mailMessage.setFrom(senderEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(bodyText);

		javaMailSender.send(mailMessage);
	}
}
