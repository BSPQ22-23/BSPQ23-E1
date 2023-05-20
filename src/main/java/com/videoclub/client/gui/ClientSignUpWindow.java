package com.videoclub.client.gui;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.client.ConnectionToServer;
import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;

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
    protected static final Logger logger = LogManager.getLogger();
    
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
		
		ConnectionToServer cts = new ConnectionToServer();
		
		panelSouth = new JPanel();
		contentPane.add(panelSouth, BorderLayout.SOUTH);
		
		btnRegister = new JButton(InternationalizationText.getString("signup"));
		panelSouth.add(btnRegister);
		
		lblLogin = new JLabel(InternationalizationText.getString("are_you_logged"));
		panelSouth.add(lblLogin);
		
		btnLogin = new JButton(InternationalizationText.getString("login"));
		panelSouth.add(btnLogin);
		
		panelCentre = new JPanel();
		contentPane.add(panelCentre, BorderLayout.CENTER);
		panelCentre.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblName = new JLabel(InternationalizationText.getString("register_name"));
		panelCentre.add(lblName);
		
		textName = new JTextField();
		panelCentre.add(textName);
		textName.setColumns(10);
		
		lblSurname = new JLabel(InternationalizationText.getString("register_surname"));
		panelCentre.add(lblSurname);
		
		textSurname = new JTextField();
		panelCentre.add(textSurname);
		textSurname.setColumns(10);
		
		lblUsername = new JLabel(InternationalizationText.getString("register_user"));
		panelCentre.add(lblUsername);
		
		textUsername = new JTextField();
		panelCentre.add(textUsername);
		textUsername.setColumns(10);
		
		lblPassword = new JLabel(InternationalizationText.getString("register_pass"));
		panelCentre.add(lblPassword);
		
		textPassword = new JTextField();
		panelCentre.add(textPassword);
		textPassword.setColumns(10);
		
		lblMail = new JLabel(InternationalizationText.getString("register_email"));
		panelCentre.add(lblMail);
		
		textMail = new JTextField();
		panelCentre.add(textMail);
		textMail.setColumns(10);
		
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				User user = new User(textUsername.getText(),textPassword.getText(), textMail.getText(), textName.getText(), textSurname.getText(), typeUser.CLIENT );
	            boolean isCorrect = cts.registerClient(user);
	            if(isCorrect) {
	            	Thread hilo = new LogInWindow();
					hilo.start();
					dispose();
	            }else {
	            	textUsername.setText("");
	            	textMail.setText("");
	            }
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

