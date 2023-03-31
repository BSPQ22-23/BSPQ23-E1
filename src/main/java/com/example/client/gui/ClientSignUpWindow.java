package com.example.client.gui;


import java.awt.EventQueue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private static JPanel panelCentre;
	private  JLabel lblNick;
	private  JTextField textNIck;
	private static JLabel lblPass;
	private static JTextField textPass;
	private static JLabel lblMail;
	private static JTextField textMail;
	private JButton btnRegister;
	private static JLabel lblLogin;
	private JButton btnLogin;
	private static final String SERVER_ENDPOINT = "http://localhost:8080/webapi";
    private static final String USERS_RESOURCE ="users";
//	private LoginController LController;

	/**
	 * Launch the application.
	 public User RegisterUser(String nickname,String password) {
		 User u = LController.RegisterUser(nickname, password);
		 return u;
	 }*/
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
		UserResource r = new UserResource();
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
		
		btnRegister = new JButton("SIIGN UP");
		panelSouth.add(btnRegister);
		
		lblLogin = new JLabel("Already signed up?");
		panelSouth.add(lblLogin);
		
		btnLogin = new JButton("LOG IN");
		panelSouth.add(btnLogin);
		
		panelCentre = new JPanel();
		contentPane.add(panelCentre, BorderLayout.CENTER);
		panelCentre.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNick = new JLabel("Insert your name:");
		panelCentre.add(lblNick);
		
		textNIck = new JTextField();
		panelCentre.add(textNIck);
		textNIck.setColumns(10);
		

		
		lblMail = new JLabel("Insert your surname:");
		panelCentre.add(lblMail);
		
		textMail = new JTextField();
		panelCentre.add(textMail);
		textNIck.setColumns(10);

		lblPass = new JLabel("Insert your email:");
		panelCentre.add(lblPass);
		
		textPass = new JTextField();
		panelCentre.add(textPass);
		textPass.setColumns(10);
		
		
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Client client = ClientBuilder.newClient();
		        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		        try {
		        	User user = new User(4, "a","b", textPass.getText(), textNIck.getText(), textMail.getText(), typeUser.ADMIN );
		            Response response = appTarget.path(USERS_RESOURCE)
		                .request(MediaType.APPLICATION_JSON)
		                .post(Entity.entity(user, MediaType.APPLICATION_JSON)
		            );

		            // check if the response was ok
		            if (response.getStatusInfo().toEnum() == Status.OK) {
		                // obtain the response data (contains a user with the new code)
		                User userCode = response.readEntity(User.class);
		                System.out.format("User registered with code %d%n", userCode.getCode());
		            } else {
		                System.out.format("Error posting a user list. %s%n", response);
		            }
		        } catch (ProcessingException j) {
		            System.out.format("Error posting a new user. %s%n", j.getMessage());
		        }
		        
		        r.getUsers("i", Order.ASC);
			//	RegisterUser(nick, pass);
				//TODO add info to the client
			
				
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
				
			}
		});
		setVisible(true);
	}

}

