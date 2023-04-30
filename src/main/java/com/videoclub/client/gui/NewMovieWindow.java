package com.videoclub.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.videoclub.client.gui.AdminMenuWindow.LogInWindow;
import com.videoclub.dao.MovieDAO;
import com.videoclub.encrypt.PasswordEncrypt;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;


public class NewMovieWindow extends JFrame {
	
	private JButton exit;
	private JButton create;
	
	
	private JTextField title;
	private JTextField genre;
	private JTextField duration;
	private JTextField year;
	private JTextField director;
	private JTextField rentalPrice;
	private JTextField empty;
	
	private JLabel text;
	
	private static final String SERVER_ENDPOINT = "http://localhost:8080/webapi";
    private static final String MOVIES_RESOURCE ="movies";
	

	private AdminMenuWindow adminMenuWindow;
	private User user;
	
	public NewMovieWindow(User user)

	{
		
		this.setTitle("DeustoVideoclubClub - New movie");
		this.setSize(900, 600);
		this.setLocation( 420, 100 );
		this.setVisible( true );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		Container cp = this.getContentPane();
		
		
		exit = new JButton("Back");
		create = new JButton("Save");
		
		
		title = new JTextField(20);
		genre = new JTextField(20);
		duration = new JTextField(20);
		year = new JTextField(20);
		director = new JTextField(20);
		rentalPrice = new JTextField(20);
		
		empty = new JTextField();
		empty.setVisible(false);
		
		text = new JLabel("Complete the following fields to introduce a new Movie");
		text.setForeground(Color.white);
		
		JPanel panelArriba = new JPanel();
		JPanel panelCentro = new JPanel();
		JPanel panelAbajo = new JPanel();
		
		
		panelArriba.setBackground(Color.red);
		text.setFont(new Font("Arial", Font.BOLD, 16));
		panelArriba.add(text);
		
		
		panelCentro.setLayout(new GridLayout(8,2));
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"Title: ",title);
		posicionaLinea(panelCentro,"Genre: ",genre);
		posicionaLinea(panelCentro,"Duration: ",duration);
		posicionaLinea(panelCentro,"Year: ",year);
		posicionaLinea(panelCentro,"Director: ",director);
		posicionaLinea(panelCentro,"RentalPrice: ",rentalPrice);
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"",empty);
		

		panelAbajo.add(create);
		panelAbajo.add(exit);
		
		cp.add(panelArriba, BorderLayout.NORTH);
		cp.add(panelCentro, BorderLayout.CENTER);
		cp.add(panelAbajo, BorderLayout.SOUTH);
		
		create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Client client = ClientBuilder.newClient();
				final WebTarget appTarget = client.target(SERVER_ENDPOINT);
				if(!title.getText().equals("") && !genre.getText().equals("") && !duration.getText().equals("") && !year.getText().equals("") && !director.getText().equals("") && !rentalPrice.getText().equals(""))
				{
					try {
						System.out.println("entro1");
						Movie m = new Movie(title.getText(),genre.getText(), Integer.parseInt(duration.getText()), Integer.parseInt(year.getText()),director.getText(), Double.parseDouble(rentalPrice.getText()));
			            Response response = appTarget.path(MOVIES_RESOURCE)
			                .request(MediaType.APPLICATION_JSON)
			                .post(Entity.entity(m, MediaType.APPLICATION_JSON)
			                		
			            );

			            // check if the response was ok
			            if (response.getStatusInfo().toEnum() == Status.OK) {
			                // obtain the response data (contains a user with the new code)
			                Movie userCode = response.readEntity(Movie.class);
			                System.out.format("Movie registered with title %s%n",userCode.getTitle());
			            } else {
			                System.out.format("Error posting a movie list. %s%n", response);
			            }
			        } catch (ProcessingException j) {
			            System.out.format("Error posting a new movie. %s%n", j.getMessage());
			        }


				}
				else
				{
					System.out.println("You have to complete all the fields");
				}			
			}
		});
		
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 Thread hilo = new AdminWindow();
					hilo.start();
					dispose();
				
			}
		});	
		
	}
	
	public static void posicionaLinea( Container cont, String etiqueta, Component campo) {
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout( new FlowLayout(FlowLayout.CENTER) ); // flow ajustado a la izquierda
		tempPanel.add( new JLabel( etiqueta ) );
		tempPanel.add( campo );
		cont.add( tempPanel );
		
	}
	
	class AdminWindow extends Thread{
		public void run() {
			if (adminMenuWindow != null) {
				adminMenuWindow.setVisible(true);
			} else {
				adminMenuWindow = new AdminMenuWindow(user);
				adminMenuWindow.setVisible(true);
			}
		}
	}
	
	/*public static void main(String[] args)
	{
		NewMovieWindow ventana = new NewMovieWindow();
		ventana.setSize(900, 600);
		ventana.setLocation( 420, 100 );
		ventana.setVisible( true );
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
	}*/

}
