package com.videoclub.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.videoclub.client.gui.NewMovieWindow.AdminWindow;
import com.videoclub.dao.MovieDAO;
import com.videoclub.pojo.Movie;

public class EditMoviesWindow extends JFrame {
	
	private JButton exit;
	private JButton save;
	private JButton delete;
	
	
	private JTextField title;
	private JTextField genre;
	private JTextField duration;
	private JTextField year;
	private JTextField director;
	private JTextField rentalPrice;
	private JTextField empty;
	
	private JLabel text;
	
	JList<String> movies;
	
	
	private HashMap<String,Movie> grupoMovies; 
	
	public EditMoviesWindow()
	{
		
		Container cp = this.getContentPane();
		
		
		exit = new JButton("Back");
		save = new JButton("Save");
		delete = new JButton("Delete");
		
		
		title = new JTextField(20);
		title.setEnabled(false);
		genre = new JTextField(20);
		genre.setEnabled(false);
		duration = new JTextField(20);
		duration.setEnabled(false);
		year = new JTextField(20);
		year.setEnabled(false);
		director = new JTextField(20);
		director.setEnabled(false);
		rentalPrice = new JTextField(20);
		rentalPrice.setEnabled(false);
		
		empty = new JTextField();
		empty.setVisible(false);
		
		text = new JLabel("Complete the following fields to introduce a new Movie");
		text.setForeground(Color.white);
		
		
		
		movies = new JList<String>();
		movies.setFixedCellWidth(200);
		movies.setFixedCellHeight(50);
		//movies.setBackground(new Color(60,141,207));
		DefaultListModel<String> lm = new DefaultListModel<String>();
		
		JScrollPane spIzquierda = new JScrollPane(movies);
		
		JPanel panelArriba = new JPanel();
		JPanel panelIzquierda = new JPanel();
		JPanel panelCentro = new JPanel();
		JPanel panelAbajo = new JPanel();
		
		
		panelIzquierda.add(spIzquierda);
		
		
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
		

		panelAbajo.add(save);
		panelAbajo.add(delete);
		panelAbajo.add(exit);
		
		cp.add(panelIzquierda, BorderLayout.WEST);
		cp.add(panelArriba, BorderLayout.NORTH);
		cp.add(panelCentro, BorderLayout.CENTER);
		cp.add(panelAbajo, BorderLayout.SOUTH);
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!title.getText().equals("") && !genre.getText().equals("") && !duration.getText().equals("") && !year.getText().equals("") && !director.getText().equals("") && !rentalPrice.getText().equals(""))
				{
					//PREGUNTAR A ASIER COMO PODRÍA ENVIAR A LA DATABASE LA MISMA MOVIE QUE ESTOY EDITANDO.
					//TODO
					Movie m = grupoMovies.get(movies.getSelectedValue());
					m.setTitle(title.getText());
					m.setGenre(genre.getText());
					m.setDuration(Integer.parseInt(duration.getText()));
					m.setYear(Integer.parseInt(year.getText()));
					m.setDirector(director.getText());
					m.setRentalPrice(Double.parseDouble(rentalPrice.getText()));
					MovieDAO.getInstance().save(m);
					System.out.println("CORRECT");
				}
				else
				{
					System.out.println("You have to complete all the fields");
				}
				
			}
		});
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// PREGUNTAR A ASIER COMO PUEDO CARGARME DE LA BASE DE DATOS UNA PELÍCULA SELECCIONADA
				grupoMovies.get(movies.getSelectedValue());
				
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
		
		movies.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (movies.getSelectedValue()!=null)
				{
					cargaDatosPeli(grupoMovies.get(movies.getSelectedValue()));
					title.setEnabled(true);
					genre.setEnabled(true);
					duration.setEnabled(true);
					year.setEnabled(true);
					director.setEnabled(true);
					rentalPrice.setEnabled(true);
					
				}
				
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
	
	public void cargarPeliculas()
	{
		DefaultListModel<String> dl = new DefaultListModel<String>();
		
		List<Movie> listMovies = MovieDAO.getInstance().getAll(Movie.class);
		
		String s;
		for(Movie m: listMovies)
		{
			grupoMovies.put(m.getTitle(), m);
			s = m.getTitle();
			dl.addElement(s);
		}
		movies.setModel(dl);
		
	}
	
	public  void cargaDatosPeli(Movie m ) {
		if (m != null) {
			title.setText(m.getTitle());
			genre.setText(m.getGenre());
			duration.setText(Integer.toString(m.getDuration()));
			year.setText(Integer.toString(m.getYear()));
			director.setText(m.getDirector());
			rentalPrice.setText(Double.toString(m.getRentalPrice()));
		}
	}
	class AdminWindow extends Thread{
		public void run() {
			AdminMenuWindow.main(null);
		}
	}
	
	public static void main(String[] args)
	{
		EditMoviesWindow ventana = new EditMoviesWindow();
		ventana.cargarPeliculas();
		ventana.setSize(900, 600);
		ventana.setLocation( 420, 100 );
		ventana.setVisible( true );
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
	}

}



