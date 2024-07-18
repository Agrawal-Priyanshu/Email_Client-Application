package client;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;

import java.util.Properties;
import java.io.File;

public class EmailSender {
	public static void sendEmailWithAttachment(String[] to,String[] cc, String subject, String body, File[] attachments) throws Exception{
	      String username = EmailSessionManager.getUsername();
	      String password = EmailSessionManager.getPassword(); 

	      Properties prop = new Properties();
	      prop.put("mail.smtp.host", "smtp.gmail.com");
	      prop.put("mail.smtp.port", "587");
	      prop.put("mail.smtp.auth", "true");
	      prop.put("mail.smtp.starttls.enable", "true"); 

	      Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
	          protected PasswordAuthentication getPasswordAuthentication() {
	              return new PasswordAuthentication(username, password);
	          }
	      });

	          Message message = new MimeMessage(session);
	          message.setFrom(new InternetAddress(username));
	          for(String TO:to) {
	        	  message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(TO,false));
	          }
	          for(String CC:cc) {
	        	  message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(CC,true));

	          }
	          message.setSubject(subject);

	          Multipart multipart = new MimeMultipart();

	          MimeBodyPart textPart = new MimeBodyPart();
	          textPart.setText(body);
	          multipart.addBodyPart(textPart);

	          for (File file : attachments) {
	              MimeBodyPart attachmentPart = new MimeBodyPart();
	              attachmentPart.attachFile(file);
	              multipart.addBodyPart(attachmentPart);
	          }

	          message.setContent(multipart);
	          Transport.send(message);
	  }
}
