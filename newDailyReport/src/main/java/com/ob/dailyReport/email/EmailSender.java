package com.ob.dailyReport.email;

import java.io.File;
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
	
	private static String from = "obdailyreport@sohu.com";

	static {
		boolean isAuth = true;
		boolean isSSL = true;
		String host = "smtp.sohu.com";
		int port = 465;
		final String username = "obdailyreport@sohu.com";
		final String password = "objectiva123";
		props.put("mail.smtp.ssl.enable", isSSL);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", isAuth);

		session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
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

			// 下面是模拟发送带附件的邮件
			// 新建一个MimeMultipart对象用来存放多个BodyPart对象
			Multipart mtp = new MimeMultipart();
			// ------设置信件文本内容------
			// 新建一个存放信件内容的BodyPart对象
			BodyPart body = new MimeBodyPart();
			// 给BodyPart对象设置内容和格式/编码方式
			body.setContent(content, "text/html");
			// 将含有信件内容的BodyPart加入到MimeMultipart对象中
			mtp.addBodyPart(body);

			BodyPart attachment = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(file.getAbsolutePath());
			DataHandler dh = new DataHandler(fds);
			attachment.setFileName(file.getName());
			attachment.setDataHandler(dh);
			mtp.addBodyPart(attachment);

			msg.setContent(mtp);

			// 以上为发送带附件的方式
			// 先进行存储邮件
			msg.saveChanges();

			Transport.send(msg);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		System.out.println("发送完毕！");
	}

	public static void sendReport(String to, File file) {
		String subject = "daily report";
		String content = "please get daily report file refer to the attachment";
		sendEmail(to,subject,content,file);
	}

}
