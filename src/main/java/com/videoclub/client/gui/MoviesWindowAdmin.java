package com.videoclub.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.client.ConnectionToServer;
import com.videoclub.pojo.Movie;

public class MoviesWindowAdmin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 902568332881306188L;
	
	private JLabel titleCatalog;
	private JLabel titleCatalog2;
	
	private JTable tableMovies;
	private JScrollPane scrollMovies;
	private DefaultTableModel modelMovies;
	
	private JButton btnBack;


	private AdminMenuWindow adminMenuWindow;


	
	private List<Movie> listMovies;
	

	public MoviesWindowAdmin() {
		
		
		
		this.setTitle(InternationalizationText.getString("deusto_movie_cat"));
		this.setSize(900, 600);
		this.setLocation( 420, 100 );
		this.setVisible( true );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		Container cont = this.getContentPane();
		
		titleCatalog = new JLabel("==================== Movies' ");
		titleCatalog.setFont(new Font("Arial", Font.ITALIC, 25));
		titleCatalog.setForeground(Color.blue);
		
		titleCatalog2 = new JLabel("Catalog ======================");
		titleCatalog2.setFont(new Font("Arial", Font.ITALIC, 25));
		titleCatalog2.setForeground(Color.blue);
		
		JPanel panelNorth = new JPanel();
		JPanel panelCenter = new JPanel();
		JPanel panelSouth = new JPanel();
		
		panelNorth.setBackground(Color.red);
		panelNorth.add(titleCatalog);
		panelNorth.add(titleCatalog2);
		
		cont.add(panelNorth, BorderLayout.NORTH);
		
		initializeTable();
		scrollMovies = new JScrollPane(tableMovies);
		
		panelCenter.setBackground(new Color(214, 234, 248));
		panelCenter.add(scrollMovies);
		cont.add(panelCenter, BorderLayout.CENTER);
		
	
		btnBack = new JButton(InternationalizationText.getString("back"));
		panelSouth.add(btnBack);
		panelSouth.setBackground(new Color(214, 234, 248));
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
	
	/**Function to initialize the table for Movies.
	 * 
	 */
	public void initializeTable() {
		
		//Cabecera
		Vector<String> header = new Vector<String>(Arrays.asList("TITLE","GENRE","DURATION","YEAR","DIRECTOR","RENTAL PRICE"));
		modelMovies = new DefaultTableModel(new Vector<Vector<Object>>(), header) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
		};
		//Create the table with the model
		tableMovies = new JTable(modelMovies);
		loadModel();
		tableMovies.setRowHeight(30);
		// Se cambia la anchura de las columnas
		tableMovies.getColumnModel().getColumn(0).setPreferredWidth(400);
		tableMovies.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableMovies.getColumnModel().getColumn(2).setPreferredWidth(100);
		tableMovies.getColumnModel().getColumn(3).setPreferredWidth(200);
		tableMovies.getColumnModel().getColumn(4).setPreferredWidth(300);
		tableMovies.getColumnModel().getColumn(5).setPreferredWidth(100);
		
		tableMovies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	/**Function to load the model.
	 * 
	 */
	public void loadModel() {
		ConnectionToServer cts = new ConnectionToServer();
		listMovies = cts.takeMovieListClient();
		while (modelMovies.getRowCount() > 0) {
			modelMovies.removeRow(0);
		}
		for(Movie m: listMovies) {
			Object [] row = {m.getTitle(),m.getGenre(),m.getDuration(),m.getYear(),m.getDirector(),m.getRentalPrice()};
			modelMovies.addRow(row);
		}

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
