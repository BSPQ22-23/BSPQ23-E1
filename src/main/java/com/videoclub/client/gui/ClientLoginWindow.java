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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ClientLoginWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7388753432723633814L;
	private JPanel contentPane;
	private JPanel panelSouth;
	private JPanel panelCentre;
	private  static JLabel lblNick;
	private JTextField textNick;
	private JLabel lblPass;
	private JTextField textPass;
	private JButton btnLogin;
	private JButton btnRegister;
	private int op;
	private boolean validador = false;
	private boolean isAdmin = false;
	
    private static final String SERVER_ENDPOINT = "http://localhost:8080/webapi";
    private static final String USERS_RESOURCE ="users";

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
		
		btnLogin = new JButton("LOG IN");
		btnRegister = new JButton("REGISTER");
		
		panelSouth.add(btnLogin);
		panelSouth.add(btnRegister);
		
		panelCentre = new JPanel();
		contentPane.add(panelCentre, BorderLayout.CENTER);
		panelCentre.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNick = new JLabel("Insert your username:");
		panelCentre.add(lblNick);
		
		textNick = new JTextField();
		panelCentre.add(textNick);
		textNick.setColumns(10);
		
		lblPass = new JLabel("Insert your password:");
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
		                System.out.println(users);
		            } else {
		                System.out.format("Error obtaining user list. %s%n", response);
		            }
		        } catch (ProcessingException o) {
		            System.out.format("Error obtaining user list. %s%n", o.getMessage());
		        }
		        
		        User user = new User(textNick.getText(),textPass.getText());
		        for (User i : users) {
		        	System.out.println(i.getUsername());
		        	if(i.getUsername().equals(user.getUsername())  && i.getPassword().equals(user.getPassword()))
		        	{
		        		validador = true;
		        		if(i.getType()==typeUser.ADMIN) {
		        			System.out.println("Trueee");
		        			isAdmin = true;
		        		}
  		
		        	}
		        	
					
				}
		        if(validador == true)
		        {
		        	System.out.println("Bienvenido " + user.getUsername());
	        		Thread registerWindow = new RegisterWindowThread();
	        		if(isAdmin) {
	        			op = 0;
	        		}else {
	        			op = 1;
	        			
	        		}
	        		ClientMenuWindow.setCodUser(user.getCode());
	        		registerWindow.start();
	        		
	        		dispose();
	        		System.out.println("tenemos que pasar a la siguiente ventana con el usuario "+user.getUsername()+" y la contrasenia:"+user.getPassword());
	        		//TODO abrir siguiente ventana
	        		
		        }
		        else
		        {
		        	System.out.println("User or password are incorrect. Please Try Again.");
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
	}
	
	class RegisterWindowThread extends Thread{
		public void run() {
			if(op == 2){
				ClientSignUpWindow.main(null);
			}else if(op == 1){
				ClientMenuWindow.main(null); 
			}else if(op==0){
				ClientMenuWindowAdmin.main(null);
			}
			
		}
	}

}
