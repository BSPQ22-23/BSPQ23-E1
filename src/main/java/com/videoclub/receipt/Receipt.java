package com.videoclub.receipt;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.google.inject.spi.Element;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.videoclub.pojo.Rental;
import com.videoclub.pojo.User;
public class Receipt {

	public static void main(String[] args) {
		new Receipt();
	}
	
	
	public static void generatepdf(User u,Rental r) throws DocumentException, MalformedURLException, IOException, AddressException, MessagingException, WriterException {
		
		 String[] opciones = new String[] {"Download", "By mail"};
		 int resp = JOptionPane.showOptionDialog(null, "Select how you want to get the Receipt", "Options", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

		if(resp==0) {
			 JFileChooser jfc = new JFileChooser();
	        int Resul = jfc.showSaveDialog(null);
	        if (Resul==JFileChooser.APPROVE_OPTION){
	            String direc = jfc.getSelectedFile().getPath();
	        
	        
				
				
				FileOutputStream file = new FileOutputStream(direc+".pdf");
		
				Document doc = new Document();
				PdfWriter.getInstance(doc, file);
				createDocument(u, r, doc);
	        }
	        
		}else if(resp ==1){
			  	Document doc = new Document();
			  	String name="Rental_"+r.getId()+"_"+u.getName()+".pdf";
			  	String comp="receipts/"+name;
	            FileOutputStream file = new FileOutputStream(comp);
	           
	            PdfWriter.getInstance(doc,file);
	            createDocument(u, r, doc);
	            sendmail(comp, name);
	            
	         
		}
		
	
	}
	
	public static void createDocument( User u, Rental r,Document doc) throws DocumentException, MalformedURLException, IOException, WriterException {
	

	    doc.open();
	    Image img = Image.getInstance("img/logo_icon_small.png");

	    
	    doc.add(img);
		doc.add(new Paragraph(" "));
		doc.add(new Paragraph("	"));
		doc.add(new Paragraph("	"));
		
	    PdfPTable table = new PdfPTable(2); // 2 columns.
	    
	    table.addCell("Rental Nº");
	    table.addCell(String.valueOf(r.getId()));
	   
	    
	    table.addCell("Movie Title");
	    table.addCell(r.getMovie().getTitle());

	    table.addCell("Movie Genre");
	    table.addCell(r.getMovie().getGenre().toString());

	    table.addCell("Movie Duration");
	    table.addCell(String.valueOf(r.getMovie().getDuration()));

	    table.addCell("Movie Year");
	    table.addCell(String.valueOf(r.getMovie().getYear()));

	    table.addCell("Movie Director");
	    table.addCell(r.getMovie().getDirector());

	    table.addCell("Movie Rental Price");
	    table.addCell(String.valueOf(r.getMovie().getRentalPrice()));

	    // Adding User details
	    table.addCell("Customer Name");
	    table.addCell(u.getName() + " " + u.getSurname());

	    table.addCell("Customer Username");
	    table.addCell(u.getUsername());

	    table.addCell("Customer Email");
	    table.addCell(u.getEmail());

	    //TODO check why rental date is null
	    table.addCell("Rental Date");
	    table.addCell(r.getRentalDate().toString());

	    table.addCell("Return Date");
	    table.addCell(r.getReturnDate().toString());

	    doc.add(table);
	    // Add thank you note
	    Paragraph thanks = new Paragraph("Thank you for renting our product. We hope you enjoy it!.");
	    doc.add(thanks);

	    // Generate QR Code
	    QRCodeWriter qrCodeWriter = new QRCodeWriter();
	    String qrCodeText="https://www.deusto.es/es/inicio/estudia/estudios/grado/ingenieria-informatica1";
	    BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 100, 100);
	    ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
	    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
	    byte[] pngData = pngOutputStream.toByteArray();

	    // Add QR code to PDF
	    Image qrCode = Image.getInstance(pngData);
	    qrCode.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
	    doc.add(qrCode);

	    // Add redeem instruction
	    Paragraph redeemInstruction = new Paragraph("Scan the above QR code to redeem your promotional offer in our physical location.");
	    doc.add(redeemInstruction);
	    doc.close();
	}
	
	
	
	  public static void sendmail(String comp,String nom) throws AddressException, MessagingException {
	 
		String mail="videoclub.deusto@gmail.com"; //mail and password are empty
		String pass="pvjynyuqubmmpoke";//LA CONTRASEÑA DEL CORREO ESTÁ QUITADA PARA QUE NO SE VEA EN EL GITHUB
		
		String mailreceiver=JOptionPane.showInputDialog(null,"Introduce your email please:");
		
		Properties p = new Properties();
			p.put("mail.smtp.host", "smtp.gmail.com");
			p.setProperty("mail.smtp.starttls.enable", "true");
			p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			p.setProperty("mail.smtp.port", "587");
			
			p.setProperty("mail.smtp.user", mail);
			p.setProperty("mail.smtp.auth", "true");
			
		Session s = Session.getDefaultInstance(p);
		BodyPart text = new MimeBodyPart();
			text.setText("Here´s your receipt. Thak you for your trust!");
		
		BodyPart adj = new MimeBodyPart();
			adj.setDataHandler(new DataHandler(new FileDataSource(comp)));
			adj.setFileName(nom);
			
		MimeMultipart m = new MimeMultipart();
			m.addBodyPart(text);
			m.addBodyPart(adj);
		
			
		
		MimeMessage message = new MimeMessage(s);
			message.setFrom(new InternetAddress(mail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailreceiver));
			message.setSubject("Your Rental´s receipt");
			
			message.setContent(m);
			
			
			
			
		Transport t = s.getTransport("smtp");
		t.connect(mail, pass);
		t.sendMessage(message, message.getAllRecipients());
		t.close();
		
		JOptionPane.showMessageDialog(null, "message succesfully sent","MESSAGE SENT",JOptionPane.INFORMATION_MESSAGE);
		
		
	}
	
	
}
