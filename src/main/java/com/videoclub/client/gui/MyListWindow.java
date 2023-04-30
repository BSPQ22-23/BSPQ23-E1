package com.videoclub.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//import com.videoclub.client.gui.ClientMenuRentals.MenuWindow;

public class MyListWindow extends JFrame {


	private static final long serialVersionUID = -1472758315632420728L;
	
	private JPanel contentPane;
	private JPanel panelS;
	private JPanel panelC;
	private JButton btnBack;
	
	//private static final String SERVER_ENDPOINT = "http://localhost:8080/webapi";
    //private static final String USERS_RESOURCE ="users";

    /**
     * Launch the application
     */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
	
			public void run() {
				try {
					MyListWindow frame = new MyListWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	
	public MyListWindow() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelS = new JPanel();
		contentPane.add(panelS, BorderLayout.SOUTH);
		
		btnBack = new JButton("BACK");
		panelS.add(btnBack);
		
		panelC = new JPanel();
		contentPane.add(panelC, BorderLayout.CENTER);
		panelC.setLayout(new GridLayout(0, 2, 0, 0));
		
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread hilo = new menuWindow();
				hilo.start();
				dispose();
				}
			
		});
	}
	class menuWindow extends Thread{
		public void run() {
			//ClientMenuWindow.main(null);
		}
	}
	
	
}
