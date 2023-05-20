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
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.videoclub.client.gui.NewMovieWindow.AdminWindow;
import com.videoclub.dao.MovieDAO;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.User;

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
	private AdminMenuWindow adminMenuWindow;
	private User user;
	

	private static final String SERVER_ENDPOINT = "http://localhost:8080/webapi";
    private static final String MOVIES_RESOURCE ="movies";
	
    
	public EditMoviesWindow(User user)
	{
		
		this.user = user;
		this.setSize(900, 600);
		this.setLocation( 420, 100 );
		
		Container cp = this.getContentPane();
		
		exit = new JButton("Back");
		save = new JButton("Save");
		delete = new JButton("Delete");
		delete.setEnabled(false);
		
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
		movies.setModel(cargarPeliculas());
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
					//TODO
					Movie m = grupoMovies.get(movies.getSelectedValue());
					m.setTitle(title.getText());
					m.setGenre(genre.getText());
					m.setDuration(Integer.parseInt(duration.getText()));
					m.setYear(Integer.parseInt(year.getText()));
					m.setDirector(director.getText());
					m.setRentalPrice(Double.parseDouble(rentalPrice.getText()));
					MovieDAO.getInstance().save(m);
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
				//TODO NO CHURRULA 
				Movie m = grupoMovies.get(movies.getSelectedValue());
				
				Client client = ClientBuilder.newClient();
		        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
				try {
		            Response response = appTarget.path(MOVIES_RESOURCE)
		                .path(m.getTitle())
		                .request()
		                .delete();

		            // check if the response was ok
		            if (response.getStatusInfo().toEnum() == Status.OK) {
		            	grupoMovies.remove(movies.getSelectedValue());
                        ((DefaultListModel<String>)movies.getModel()).removeElement(m.getTitle());
		                System.out.println("Movie correctly deleted from server");
		            } else {
		                System.out.format("Error deleting a movie list. %s%n", response);
		            }
		        } catch (ProcessingException o) {
		            System.out.format("Error posting a new movie. %s%n", o.getMessage());
		        }
				
				delete.setEnabled(false);
				title.setText("");
				genre.setText("");
				duration.setText("");
				year.setText("");
				director.setText("");
				rentalPrice.setText("");
				
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
					delete.setEnabled(true);
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
	
	public DefaultListModel<String> cargarPeliculas()
	{
		HashMap<String,Movie> grupo = new HashMap<String,Movie>();
		Client client = ClientBuilder.newClient();
        final WebTarget appTarget = client.target(SERVER_ENDPOINT);
		List<Movie> listMovies = null;
		try {
            Response response = appTarget.path(MOVIES_RESOURCE)
                .request(MediaType.APPLICATION_JSON)
                .get();

            // check that the response was HTTP OK
            if (response.getStatusInfo().toEnum() == Status.OK) {
                // the response is a generic type (a List<User>)
                GenericType<List<Movie>> listType = new GenericType<List<Movie>>(){};
                listMovies = response.readEntity(listType);
                
            } else {
                System.out.format("Error obtaining movie list. %s%n", response);
            }
        } catch (ProcessingException o) {
            System.out.format("Error obtaining movie list. %s%n", o.getMessage());
        }
		
		DefaultListModel<String> dl = new DefaultListModel<String>();
		String s;
		for(Movie m: listMovies)
		{
			grupo.put(m.getTitle(), m);
			s = m.getTitle();
			
			dl.addElement(s);
		}
		grupoMovies = grupo;
		return dl;
		
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
		EditMoviesWindow ventana = new EditMoviesWindow();
		ventana.setSize(900, 600);
		ventana.setLocation( 420, 100 );
		ventana.setVisible( true );
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
	}*/

}



