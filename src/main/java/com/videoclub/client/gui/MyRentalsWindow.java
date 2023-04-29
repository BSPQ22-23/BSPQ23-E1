package com.videoclub.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
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

import com.videoclub.client.gui.MoviesWindow.menuWindow;
import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.RentalDAO;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.Rental;

public class MyRentalsWindow extends JFrame {

	
	private static final long serialVersionUID = 5282904525668243129L;
	private JLabel title;
	private JLabel title2;
	private JTable tableRentals;
	private JScrollPane scrollRentals;
	private DefaultTableModel modelRentals;
	private JButton btnBack;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					MyRentalsWindow frame = new MyRentalsWindow();
					frame.setTitle("DeustoVideoClub - My history of rentals");
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
	}
	
	public MyRentalsWindow() {
		
		Container cont = this.getContentPane();
		
		title = new JLabel("==================== My ");
		title.setFont(new Font("Arial", Font.ITALIC, 25));
		title.setForeground(Color.blue);
		
		title2 = new JLabel("rentals ======================");
		title2.setFont(new Font("Arial", Font.ITALIC, 25));
		title2.setForeground(Color.blue);
		
		JPanel panelNorth = new JPanel();
		JPanel panelCenter = new JPanel();
		JPanel panelSouth = new JPanel();
		
		panelNorth.setBackground(Color.red);
		panelNorth.add(title);
		panelNorth.add(title2);
		cont.add(panelNorth, BorderLayout.NORTH);
		
		initializeTable();
		scrollRentals= new JScrollPane(tableRentals);
		panelCenter.setBackground(new Color(214, 234, 248));
		panelCenter.add(scrollRentals);
		cont.add(panelCenter, BorderLayout.CENTER);
		
		btnBack = new JButton("BACK");
		panelSouth.add(btnBack);
		panelSouth.setBackground(new Color(214, 234, 248));
		cont.add(panelSouth, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
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
		Vector<String> header = new Vector<String>(Arrays.asList("ID","TITLE","CUSTOMER","RENTAL DATE", "RETURN DATE"));		
		modelRentals = new DefaultTableModel(new Vector<Vector<Object>>(), header) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
		};
		//Create the table with the model
		tableRentals = new JTable(modelRentals);
		loadModel();
		tableRentals.setRowHeight(30);
		// Se cambia la anchura de las columnas
		tableRentals.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableRentals.getColumnModel().getColumn(1).setPreferredWidth(400);
		tableRentals.getColumnModel().getColumn(2).setPreferredWidth(100);
		tableRentals.getColumnModel().getColumn(3).setPreferredWidth(100);
		tableRentals.getColumnModel().getColumn(4).setPreferredWidth(100);
		
		tableRentals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public void loadModel() {
		
		List<Rental> listRentals = RentalDAO.getInstance().getAll();
		while (modelRentals.getRowCount() > 0) {
			modelRentals.removeRow(0);
		}
		for(Rental r: listRentals) {
			Object [] row = {r.getId(), r.getMovie().getTitle(), r.getCustomer().getCode(), r.getRentalDate(), r.getReturnDate()};
			modelRentals.addRow(row);
		}
	}
	
	class menuWindow extends Thread {
		public void run() {
			ClientMenuWindow.main(null);
		}
	}
	


}
