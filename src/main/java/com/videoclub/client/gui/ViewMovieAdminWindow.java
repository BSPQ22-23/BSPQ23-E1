package com.videoclub.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.client.gui.MoviesWindow.menuWindow;
import com.videoclub.dao.RentalDAO;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.Rental;
import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;

public class ViewMovieAdminWindow extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4208736352277026192L;
	private JLabel title;
	private JLabel title2;
	private JLabel movieTitle, director, duration, rentalPrice, genre;
	private JButton btnBack;

	
	private AdminMenuWindow adminMenuWindow;
	private User user;
	
	public ViewMovieAdminWindow(User user, Movie m) {
		
		this.user = user;
		
		this.setTitle(InternationalizationText.getString("deusto_rental_hist"));
		this.setSize(900, 300);
		this.setLocation( 420, 100 );
		this.setVisible( true );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		Container cont = this.getContentPane();
		
		title = new JLabel("==================== View movie");
		title.setFont(new Font("Arial", Font.ITALIC, 25));
		title.setForeground(Color.blue);
		
		title2 = new JLabel(" details ======================");
		title2.setFont(new Font("Arial", Font.ITALIC, 25));
		title2.setForeground(Color.blue);
		
		JPanel panelNorth = new JPanel();
		JPanel panelCenter = new JPanel();
		JPanel panelSouth = new JPanel();
		
		panelNorth.setBackground(Color.red);
		panelNorth.add(title);
		panelNorth.add(title2);
		cont.add(panelNorth, BorderLayout.NORTH);
		
		
		panelCenter.setLayout(new GridLayout(5,1));
		movieTitle = new JLabel(InternationalizationText.getString("title")+": " + m.getTitle());
		movieTitle.setHorizontalAlignment(JLabel.CENTER);
		panelCenter.add(movieTitle);
		genre = new JLabel(InternationalizationText.getString("genre")+": " + m.getGenre());
		genre.setHorizontalAlignment(JLabel.CENTER);
		panelCenter.add(genre);
		duration = new JLabel(InternationalizationText.getString("duration")+": " + m.getDuration());
		duration.setHorizontalAlignment(JLabel.CENTER);
		panelCenter.add(duration);
		director = new JLabel(InternationalizationText.getString("director")+": " + m.getDirector());
		director.setHorizontalAlignment(JLabel.CENTER);
		panelCenter.add(director);
		rentalPrice = new JLabel(InternationalizationText.getString("price")+": " + m.getRentalPrice());
		rentalPrice.setHorizontalAlignment(JLabel.CENTER);
		panelCenter.add(rentalPrice);
		panelCenter.setBackground(new Color(214, 234, 248));
		cont.add(panelCenter,BorderLayout.CENTER);
		

		btnBack = new JButton(InternationalizationText.getString("back"));
		panelSouth.add(btnBack);
		cont.add(panelSouth, BorderLayout.SOUTH);
		
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread hilo = new menuWindow();
				hilo.start();
				dispose();
				}
			});
		
		
		
	}
	
	class menuWindow extends Thread {
		public void run() {
			if (adminMenuWindow != null) {
				adminMenuWindow.setVisible(true);
			} else {
				adminMenuWindow = new AdminMenuWindow();
				adminMenuWindow.setVisible(true);
			}
		}
	}

}
