package com.psk.pms.utils;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class MailClient {

	private MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String to, String userName) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("srirampasupathykumar@gmail.com");
		message.setTo(to);
		message.setSubject("PSK - Your Account Details");
		message.setText("Dear User,"
				+ "\n\n"
				+ "Seasons greetings and a warm welcome to the PSK family !"
				+ "\n\n"
				+ "Your User Name is  "
				+ userName
				+ ". You will be able to login to application once our admin approves your request.You will be receiving a notification mail on same."
				+ "\n\n" + "Regards," + "\n" + "PSK Team.");
		mailSender.send(message);
	}

}
