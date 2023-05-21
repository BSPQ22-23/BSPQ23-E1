package com.videoclub.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.client.ConnectionToServer;
import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.RentalDAO;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.Rental;
import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;
import com.videoclub.receipt.Receipt;

public class RentalWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 902568332881306188L;
	
	private JLabel titleCatalog;
	private JLabel titleCatalog2;
	
	private JTable tableRentals;
	private JScrollPane scrollRentals;
	private DefaultTableModel modelRentals;
	
	private JButton btnBack;


	private AdminMenuWindow adminMenuWindow;


	
	private List<Rental> listRentals;
	

	public RentalWindow() {
		
		
		
		this.setTitle(InternationalizationText.getString("deusto_movie_cat"));
		this.setSize(900, 600);
		this.setLocation( 420, 100 );
		this.setVisible( true );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		Container cont = this.getContentPane();
		
		titleCatalog = new JLabel("==================== Rentals' ");
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
		scrollRentals = new JScrollPane(tableRentals);
		
		panelCenter.setBackground(new Color(214, 234, 248));
		panelCenter.add(scrollRentals);
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
	
	public void initializeTable() {
		
		//Cabecera
		Vector<String> header = new Vector<String>(Arrays.asList("MOVIE","CUSTOMER","RENTALDATE","RETURNDATE"));
		modelRentals = new DefaultTableModel(new Vector<Vector<Object>>(), header) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
		};
		//Create the table with the model
		tableRentals = new JTable(modelRentals);
		loadModel();
		tableRentals.setRowHeight(30);
		// Se cambia la anchura de las columnas
		tableRentals.getColumnModel().getColumn(0).setPreferredWidth(300);
		tableRentals.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableRentals.getColumnModel().getColumn(2).setPreferredWidth(700);
		tableRentals.getColumnModel().getColumn(3).setPreferredWidth(700);

		
		tableRentals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public void loadModel() {
		ConnectionToServer cts = new ConnectionToServer();
		//TODO Tiene que llamar al Server, esto esta mal.
		//--------------------------------------------------
		listRentals = cts.takeRentalListClient();
		//--------------------------------------------------
		while (modelRentals.getRowCount() > 0) {
			modelRentals.removeRow(0);
		}
		for(Rental r: listRentals) {
			Object [] row = {r.getMovie().getTitle(),r.getCustomer().getUsername(),r.getRentalDate(),r.getReturnDate()};
			modelRentals.addRow(row);
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
