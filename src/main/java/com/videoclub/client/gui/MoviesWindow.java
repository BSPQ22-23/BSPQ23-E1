package com.videoclub.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.DocumentException;
import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.RentalDAO;
import com.videoclub.dao.UserDAO;
import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;
import com.videoclub.receipt.Receipt;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.Rental;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MoviesWindow extends JFrame {

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
	private JButton btnRent;

	private AdminMenuWindow adminMenuWindow;
	private ClientMenuWindow clientMenuWindow;
	private User user;
	
	private List<Movie> listMovies;
	
	
	
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MoviesWindow frame = new MoviesWindow();
					frame.setTitle("DeustoVideoClub - Movies' Cartalog");
					frame.setVisible(true);
					frame.setSize(900, 600);
					frame.setLocation( 420, 100 );
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	public MoviesWindow(User user) {
		
		this.user = user;
		
		this.setTitle("DeustoVideoClub - Movies Catalog");
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
		
		btnRent = new JButton("Rent");
		panelSouth.add(btnRent);
		btnBack = new JButton("BACK");
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
		btnRent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = tableMovies.getSelectedRow();
				Movie selectedMovie = listMovies.get(row);
				Rental r = new Rental(selectedMovie,user,new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
				RentalDAO.getInstance().save(r);
				try {
					Receipt.generatepdf(user, r);
				} catch (DocumentException | IOException | MessagingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		}
	
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
	
	public void loadModel() {
		
		listMovies = MovieDAO.getInstance().getAll(Movie.class);
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
			if (user.getType() == typeUser.ADMIN) {
				if (adminMenuWindow != null) {
					adminMenuWindow.setVisible(true);
				} else {
					adminMenuWindow = new AdminMenuWindow(user);
					adminMenuWindow.setVisible(true);
				}
			} else {
				if (clientMenuWindow != null) {
					clientMenuWindow.setVisible(true);
				} else {
					clientMenuWindow = new ClientMenuWindow(user);
					clientMenuWindow.setVisible(true);
				}
			}
		}
	}
}
