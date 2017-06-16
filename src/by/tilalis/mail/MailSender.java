package by.tilalis.mail;

import java.util.Properties;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@MessageDriven
public class MailSender implements MessageListener {
    public MailSender() {
    }
	
    public void onMessage(javax.jms.Message message) {
    	final TextMessage textMessage = (TextMessage) message;
    	final String username = "thebestcpsstoreever@gmail.com";
		final String password = "thebestcpsstoreeverpassword";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {
	    	final String messageText = textMessage.getText(); 
			Message email = new MimeMessage(session);
			email.setFrom(new InternetAddress("thebestcpsstoreever@gmail.com"));
			email.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("alexvolshtein@gmail.com"));
			email.setSubject("Order Info");
			email.setText(messageText);
			Transport.send(email);
		} catch (MessagingException | JMSException e) {
			e.printStackTrace();
		}
    }

}
