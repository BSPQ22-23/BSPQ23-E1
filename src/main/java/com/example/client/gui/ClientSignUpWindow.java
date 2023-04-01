package com.example.client.gui;


import java.awt.EventQueue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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

import com.example.UserResource;
import com.example.UserResource.Order;
import com.example.pojo.User;
import com.example.pojo.typeUser;

//import client.controller.LoginController;
//import server.data.domain.User;

public class ClientSignUpWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8743514429828691121L;
	private JPanel contentPane;
	private JPanel panelSouth;
	private JPanel panelCentre;
	private JLabel lblUsername;
	private JTextField textUsername;
	private JLabel lblPassword;
	private JTextField textPassword;
	private JLabel lblName;
	private JTextField textName;
	private JLabel lblSurname;
	private JTextField textSurname;
	private JLabel lblMail;
	private JTextField textMail;
	private JButton btnRegister;
	private static JLabel lblLogin;
	private JButton btnLogin;
	private static final String SERVER_ENDPOINT = "http://localhost:8080/webapi";
    private static final String USERS_RESOURCE ="users";
    private int n = 1;
    Client client = ClientBuilder.newClient();
    final WebTarget appTarget = client.target(SERVER_ENDPOINT);
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientSignUpWindow frame = new ClientSignUpWindow();
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
	public ClientSignUpWindow() {
		setTitle("SIGN UP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelSouth = new JPanel();
		contentPane.add(panelSouth, BorderLayout.SOUTH);
		
		btnRegister = new JButton("SIGN UP");
		panelSouth.add(btnRegister);
		
		lblLogin = new JLabel("Already signed up?");
		panelSouth.add(lblLogin);
		
		btnLogin = new JButton("LOG IN");
		panelSouth.add(btnLogin);
		
		panelCentre = new JPanel();
		contentPane.add(panelCentre, BorderLayout.CENTER);
		panelCentre.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblName = new JLabel("Insert your Name:");
		panelCentre.add(lblName);
		
		textName = new JTextField();
		panelCentre.add(textName);
		textName.setColumns(10);
		
		lblSurname = new JLabel("Insert your Surname:");
		panelCentre.add(lblSurname);
		
		textSurname = new JTextField();
		panelCentre.add(textSurname);
		textSurname.setColumns(10);
		
		lblUsername = new JLabel("Insert your username:");
		panelCentre.add(lblUsername);
		
		textUsername = new JTextField();
		panelCentre.add(textUsername);
		textUsername.setColumns(10);
		
		lblPassword = new JLabel("Insert your password:");
		panelCentre.add(lblPassword);
		
		textPassword = new JTextField();
		panelCentre.add(textPassword);
		textPassword.setColumns(10);
		
		lblMail = new JLabel("Insert your email:");
		panelCentre.add(lblMail);
		
		textMail = new JTextField();
		panelCentre.add(textMail);
		textMail.setColumns(10);

		
		
		
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
		        try {
		        	User user = new User(n, textUsername.getText(),textPassword.getText(), textMail.getText(), textName.getText(), textSurname.getText(), typeUser.CLIENT );
		            Response response = appTarget.path(USERS_RESOURCE)
		                .request(MediaType.APPLICATION_JSON)
		                .post(Entity.entity(user, MediaType.APPLICATION_JSON)
		            );

		            // check if the response was ok
		            if (response.getStatusInfo().toEnum() == Status.OK) {
		                // obtain the response data (contains a user with the new code)
		                User userCode = response.readEntity(User.class);
		                System.out.format("User registered with code %d%n",userCode.getCode());
		            } else {
		                System.out.format("Error posting a user list. %s%n", response);
		            }
		        } catch (ProcessingException j) {
		            System.out.format("Error posting a new user. %s%n", j.getMessage());
		        }
		        n++;
		        
		        Thread hilo = new LogInWindow();
				hilo.start();
				dispose();
		        
			//	RegisterUser(nick, pass);
				//TODO add info to the client
			
				
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					Thread hilo = new LogInWindow();
					hilo.start();
					dispose();
			}
		});
		setVisible(true);
	}

	
	class LogInWindow extends Thread{
		public void run() {
			ClientLoginWindow.main(null);
		}
	}
}

