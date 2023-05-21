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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.client.gui.MoviesWindow.menuWindow;
import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.RentalDAO;
import com.videoclub.dao.UserDAO;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.Rental;
import com.videoclub.pojo.User;

public class MyRentalsWindow extends JFrame {

	
	private static final long serialVersionUID = 5282904525668243129L;
	private JLabel title;
	private JLabel title2;
	private JTable tableRentals;
	private JScrollPane scrollRentals;
	private DefaultTableModel modelRentals;
	private JButton btnBack;
	
	private ClientMenuWindow clientMenuWindow;
	private User user;
	protected static final Logger logger = LogManager.getLogger();
	
	private List<Rental> listRentals;
	
	/*public static void main(String[] args) {
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
	}*/
	
	public MyRentalsWindow(User user) {
		
		this.user = user;
		
		this.setTitle(InternationalizationText.getString("deusto_rental_hist"));
		this.setSize(900, 600);
		this.setLocation( 420, 100 );
		this.setVisible( true );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
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
		
		listRentals = RentalDAO.getInstance().getAll();
		while (modelRentals.getRowCount() > 0) {
			modelRentals.removeRow(0);
		}
		if (listRentals != null) {
			for(Rental r: listRentals) {
				logger.info(r.getCustomer().getCode() == user.getCode());
				if (r.getCustomer().getCode() == user.getCode()) {
					Object [] row = {r.getId(), r.getMovie().getTitle(), r.getCustomer().getCode(), r.getRentalDate(), r.getReturnDate()};
					modelRentals.addRow(row);
				}
			}
		}
	}
	class menuWindow extends Thread {
		public void run() {
			if (clientMenuWindow != null) {
				clientMenuWindow.setVisible(true);
			} else {
				clientMenuWindow = new ClientMenuWindow(user);
				clientMenuWindow.setVisible(true);
			}
		}
	}
	
	class addRentalWindow extends Thread {
		public void run() {
		}
	}

}
