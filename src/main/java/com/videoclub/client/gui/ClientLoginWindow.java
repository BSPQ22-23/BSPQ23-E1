package com.videoclub.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.log.SysoCounter;
import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.client.ConnectionToServer;
import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.RentalDAO;
import com.videoclub.dao.UserDAO;
import com.videoclub.encrypt.PasswordEncrypt;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.Rental;
import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;

public class ClientLoginWindow extends JFrame {

	private static final long serialVersionUID = -9041853324743460311L;
	
	private JPanel contentPane;
	private JPanel panelSouth;
	private JPanel panelCentre;
	private JLabel lblNick;
	private JTextField textNick;
	private JLabel lblPass;
	private JTextField textPass;
	private JButton btnLogin;
	private JButton btnRegister;
	private JButton changeLang;
	private int op;
	protected static final Logger logger = LogManager.getLogger();
    
    private ClientMenuWindow clientMenuWindow;
    private AdminMenuWindow adminMenuWindow;
    private User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User u1 = new User("admin", PasswordEncrypt.encryptPassword("admin"), "admin@email.com", "AdminName", "AdminSurname", typeUser.ADMIN);
					UserDAO.getInstance().save(u1);
					ClientLoginWindow frame = new ClientLoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientLoginWindow() {
		ConnectionToServer cts = new ConnectionToServer();

		setTitle("LogIn Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelSouth = new JPanel();
		contentPane.add(panelSouth, BorderLayout.SOUTH);
		
		btnLogin = new JButton(InternationalizationText.getString("login"));
		btnRegister = new JButton(InternationalizationText.getString("register"));
		changeLang = new JButton(InternationalizationText.getString("changelang"));
		
		panelSouth.add(btnLogin);
		panelSouth.add(btnRegister);
		panelSouth.add(changeLang);
		
		panelCentre = new JPanel();
		contentPane.add(panelCentre, BorderLayout.CENTER);
		panelCentre.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNick = new JLabel(InternationalizationText.getString("login_user"));
		panelCentre.add(lblNick);
		
		textNick = new JTextField();
		panelCentre.add(textNick);
		textNick.setColumns(10);
		
		lblPass = new JLabel(InternationalizationText.getString("login_pass"));
		panelCentre.add(lblPass);
		
		textPass = new JPasswordField();
		panelCentre.add(textPass);
		textPass.setColumns(10);
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				user = cts.logInClient(textNick.getText(), textPass.getText());
		        if(user != null) {
			        if(user.getType() == typeUser.ADMIN) {
			        	op = 0;
			        }else if(user.getType() == typeUser.CLIENT) {
			        	op = 1;
			        }
		        	Thread registerWindow = new RegisterWindowThread();
		        	ClientMenuWindow.setCodUser(user.getCode());
	        		dispose();
	        		registerWindow.start();
		        }else {
		        	
		        }
			}
		});
		
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread registerWindow = new RegisterWindowThread();
				op =2;
				registerWindow.start();
				dispose();
			}
		});
		setVisible(true);
		
		changeLang.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(InternationalizationText.language == "en") {
					InternationalizationText.setLanguage("es");
					logger.info("Language changed to Spanish");
				}else {
					InternationalizationText.setLanguage("en");
					logger.info("Language changed to English");
				}
				new ClientLoginWindow();
				dispose();
			}
		});
		
	}
	
	class RegisterWindowThread extends Thread{
		public void run() {
			if(op == 2){
				ClientSignUpWindow.main(null);
			}else if(op == 1){
				//ClientMenuWindow.main(null); 
				if (clientMenuWindow != null) {
					clientMenuWindow.setVisible(true);
				} else {
					clientMenuWindow = new ClientMenuWindow(user);
					clientMenuWindow.setVisible(true);
				}
			}else if(op==0){
				if (adminMenuWindow != null) {
					adminMenuWindow.setVisible(true);
				} else {
					adminMenuWindow = new AdminMenuWindow(user);
					adminMenuWindow.setVisible(true);
				}
			}
			
		}
	}

}
