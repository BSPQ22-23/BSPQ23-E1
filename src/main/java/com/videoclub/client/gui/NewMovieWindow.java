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

import com.videoclub.client.gui.AdminMenuWindow.LogInWindow;
import com.videoclub.dao.MovieDAO;
import com.videoclub.pojo.Movie;

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
	
	public NewMovieWindow()
	{
		
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
				if(!title.getText().equals("") && !genre.getText().equals("") && !duration.getText().equals("") && !year.getText().equals("") && !director.getText().equals("") && !rentalPrice.getText().equals(""))
				{
					Movie m = new Movie(title.getText(),genre.getText(), Integer.parseInt(duration.getText()), Integer.parseInt(year.getText()),director.getText(), Double.parseDouble(rentalPrice.getText()));
					MovieDAO.getInstance().save(m);
					System.out.println("CORRECT");
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
			AdminMenuWindow.main(null);
		}
	}
	
	public static void main(String[] args)
	{
		NewMovieWindow ventana = new NewMovieWindow();
		ventana.setSize(900, 600);
		ventana.setLocation( 420, 100 );
		ventana.setVisible( true );
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
	}

}
