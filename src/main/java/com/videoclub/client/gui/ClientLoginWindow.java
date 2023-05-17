package com.videoclub.client.gui;

import java.awt.EventQueue;


import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.dao.UserDAO;
import com.videoclub.encrypt.PasswordEncrypt;
import com.videoclub.pojo.ClassColumnNames;
import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

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
	private boolean validador = false;
	private boolean isAdmin = false;
	protected static final Logger logger = LogManager.getLogger();
	
    private static final String SERVER_ENDPOINT = "http://localhost:8080/webapi";
    private static final String USERS_RESOURCE ="users";
    
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
				Client client = ClientBuilder.newClient();
		        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		        List<User> users = null;
		        try {
		            Response response = appTarget.path(USERS_RESOURCE)
		                .request(MediaType.APPLICATION_JSON)
		                .get();

		            // check that the response was HTTP OK
		            if (response.getStatusInfo().toEnum() == Status.OK) {
		                // the response is a generic type (a List<User>)
		                GenericType<List<User>> listType = new GenericType<List<User>>(){};
		                users = response.readEntity(listType);
		                
		            } else {
		                logger.info("Error obtaining user list. %s%n", response);
		            }
		        } catch (ProcessingException o) {
		           	logger.info("Error obtaining user list. %s%n", o.getMessage());
		        }
		        
		        user = UserDAO.getInstance().find(textNick.getText(), User.ColumnsNameUser.username);
		        
		        if(users != null) {
		        for (User i : users) {
		        	
		        	
		        	if(i.getUsername().equals(user.getUsername())  && i.getPassword().equals(user.getPassword()))
		        	{
		        		validador = true;
		        		if(i.getType()==typeUser.ADMIN) {
		        			isAdmin = true;
		        		}
  		
		        	}
		        	
					
				}
		        }
		        
		        if(validador == true)
		        {
		        	logger.info("Welcome " + user.getUsername());
	        		Thread registerWindow = new RegisterWindowThread();
	        		if(isAdmin) {
	        			op = 0;
	        		}else {
	        			op = 1;
	        			
	        		}
	        
	        		 
	        		ClientMenuWindow.setCodUser(user.getCode());
	        		dispose();
	        		registerWindow.start();
	        		
	        		
		        }
		        else
		        {
		        	logger.info("User or password are incorrect. Please Try Again.");
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
				}else {
					InternationalizationText.setLanguage("en");
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
