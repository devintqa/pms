package com.psk.pms.utils;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MailClient {

	private JavaMailSender mailSender;

	private static final Logger LOGGER = Logger.getLogger(MailClient.class);

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String to, String userName) {
		MimeMessage message = mailSender.createMimeMessage();
		String msg = "Dear User,"
				+ "<br><br>"
				+ "Seasons greetings and a warm welcome to the PSK family !"
				+ "<br><br>"
				+ "Your User Name is  "
				+ "<b>"
				+ userName
				+ "</b>"
				+ ". You will be able to login to application once our admin approves your request.You will be receiving a notification mail on same."
				+ "<br><br>" + "Regards," + "<br>" + "PSK Team.";
		sendMail(to,userName,message,msg);
	}

	public void sendAccessMail(String to, String userName, String action) {
		MimeMessage message = mailSender.createMimeMessage();
		String msg = "";
		if ("1".equalsIgnoreCase(action)) {
			msg = "Dear User,"
					+ "<br><br>"
					+ "Your account has been "
					+ "<b>"
					+ "ENABLED"
					+ "</b>"
					+ " by the Admin."
					+ "<br>"
					+ "Now you can start using PMS Application to log into the system."
					+ "<br><br>" + "Regards," + "<br>" + "PSK Team.";
		} else if ("2".equalsIgnoreCase(action)) {
			msg = "Dear User," + "<br><br>" + "Sorry !! Your account has been "
					+ "<b>" + "DENIED" + "</b>" + " by the Admin." + "<br><br>"
					+ "Regards," + "<br>" + "PSK Team.";
		}
		sendMail(to,userName,message,msg);
	}
	
	private void sendMail(String to, String userName, MimeMessage message,String msg){
		try {
			message.setSubject("PSK - Your Account Details");
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom("pskconstructionsgroup@gmail.com");
			helper.setTo(to);
			helper.setText(msg, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			LOGGER.error("Error in sending mail to the user :" + userName);
			LOGGER.error("Error :" + e.getMessage());
		}
	}

}
