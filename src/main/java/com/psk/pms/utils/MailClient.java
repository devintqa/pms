package com.psk.pms.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailClient {

	private JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String to, String userName) {
		MimeMessage message = mailSender.createMimeMessage();
        String msg= "Dear User," +"<br><br>"+ "Seasons greetings and a warm welcome to the PSK family !" +"<br><br>"+ "Your User Name is  " +"<b>"+ userName + "</b>" +". You will be able to login to application once our admin approves your request.You will be receiving a notification mail on same." +"<br><br>"+ "Regards,"+"<br>"+ "PSK Team.";
		try {
			message.setSubject("PSK - Your Account Details");
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom("pskconstructionsgroup@gmail.com");
			helper.setTo(to);
			helper.setText(msg, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			System.out.println("Error in sending mail to the user :" + userName);
		}
	}
	
	public void sendAccessMail(String to, String userName, String action) {
		MimeMessage message = mailSender.createMimeMessage();
		String msg = "";
		if(action.equalsIgnoreCase("1")){
        msg= "Dear User," +"<br><br>"+ "Your account has been " +"<b>"+ "ENABLED" + "</b>" + " by the Admin." +"<br>"+ "Now you can start using PMS Application to log into the system." +"<br><br>"+ "Regards,"+"<br>"+ "PSK Team.";}
        else if(action.equalsIgnoreCase("2")){
        msg= "Dear User," +"<br><br>"+ "Sorry !! Your account has been " +"<b>"+ "DENIED" + "</b>" + " by the Admin." +"<br><br>"+ "Regards,"+"<br>"+ "PSK Team.";}
		try {
			message.setSubject("PSK - Your Access Details");
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom("pskconstructionsgroup@gmail.com");
			helper.setTo(to);
			helper.setText(msg, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			System.out.println("Error in sending mail to the user :" + userName);
		}
	}

}