package com.ob.dailyReport.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {

	private static Properties props = new Properties();

	private static Session session;

	private static String from = "dailyReportServer@objectivasoftware";

	static {
		String host = "10.32.148.123";
		int port = 25;
		// final String username = "obdailyreport@sohu.com";
		// final String password = "objectiva123";
		// props.put("mail.smtp.ssl.enable", true);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", false);

		session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("", "");
			}
		});

	}

	public static void sendEmail(String to, String subject, String content, File file) {
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(subject);
			// msg.setText("this is test content");

			Multipart mtp = new MimeMultipart();
			BodyPart body = new MimeBodyPart();
			body.setContent(content, "text/html");
			mtp.addBodyPart(body);

			BodyPart attachment = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(file.getAbsolutePath());
			DataHandler dh = new DataHandler(fds);
			attachment.setFileName(file.getName());
			attachment.setDataHandler(dh);
			mtp.addBodyPart(attachment);
			msg.setContent(mtp);
			msg.saveChanges();
			Transport.send(msg);
			System.out.println("send email to :" + to + " successfully! at " + new Date());
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public static void sendReport(String to, File file) {
		String subject = "daily report";
		String content = "please get daily report file refer to the attachment";
		sendEmail(to, subject, content, file);
	}

	public static void main(String[] args) {
		String to = "coreyqin@objectivasoftware.com";
		File file = new File("pom.xml");
		sendReport(to, file);
	}

}
