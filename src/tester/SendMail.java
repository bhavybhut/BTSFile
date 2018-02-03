package tester;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

	private String from;
	private String to;
	private String subject;
	private String desc;
	
	public SendMail(String from,String to,String subject,String desc){
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.desc = desc;
	}
	
	public void send(){
		Properties props = new Properties();
		String pass = "pooja@20091990";
		String host = "smtp.gmail.com";
		
		props.put("mail.smtp.starttls.enable", "true"); // added this line
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.user", from);
	    props.put("mail.smtp.password", pass);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");
		
		Session mail = Session.getDefaultInstance(props,null);
		
		
		Message msg = new MimeMessage(mail);
		InternetAddress fromAddress = null;
		InternetAddress toAddress = null;
		Connection conn;
		PreparedStatement stmt;
		Statement stmt1;
		ResultSet rs;
		int d_id;
		try{
			fromAddress = new InternetAddress(from);
			toAddress = new InternetAddress(to);
		}
		catch(AddressException e){e.printStackTrace();}
		try{
			msg.setFrom(fromAddress);
			msg.setRecipient(RecipientType.TO, toAddress);
			msg.setSubject(subject);
			msg.setText(desc);
			
			Transport transport = mail.getTransport("smtp");
		    transport.connect(host, from, pass);
		    transport.sendMessage(msg, msg.getAllRecipients());
			}
		catch (MessagingException e) {
			e.printStackTrace();
		}
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt1 = conn.createStatement();
			rs = stmt1.executeQuery("select u_id from user where email='"+to+"'");
			while(rs.next()){
				d_id = rs.getInt(1);
				stmt = conn.prepareStatement("insert into mail(d_id,subject,text) values(?,?,?)");
				stmt.setInt(1, d_id);
				stmt.setString(2, subject);
				stmt.setString(3, desc);
				stmt.executeUpdate();
			}
		}catch(Exception e){e.printStackTrace();}
	}
}