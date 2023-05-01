package com.videoclub.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.videoclub.dao.MovieDAO;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.User;




public class ClientMenuWindow extends JFrame{

	
	private static final long serialVersionUID = 2702524318727010309L;
	
	private JLabel menuAreaPersonal;
	private JLabel menuAreaDeustoFlix;
	private JLabel menuAreaPersonal2;
	private JLabel menuAreaDeustoFlix2;
	
	private JButton menuMiLista;
	private JButton menuMyRentals;
	private JButton menuFiltro;
	private JButton menuCambiarUsuario;
	private JButton menuCatalogoSeries;
	private JButton menuCatalogoPelicula;
	private JButton menuDatosUsuario;
	private JButton menuHistorial;
	private JButton menuRecentMovies;
	
	private JLabel menuDeustoFlix;
	
	private static int CodUser;
	private MyRentalsWindow myRentalsWindow;
	private MoviesWindow moviesWindow;
	private ViewMovieWindow viewMovieWindow;
	private Movie movie;
	private User user;
	
	public static void main(String[] args) {
		
	}
	
	public ClientMenuWindow(User user) {
		
		this.user = user;
		
		this.setTitle("DeustoVideoClub - Menu");
		this.setSize(900, 600);
		this.setLocation( 420, 100 );
		this.setVisible( true );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		//Creamos el contenedor donde vamos a colocar todo
		Container cp = this.getContentPane();
		
		//Creamos los componentes con sus especificaciones
		menuDatosUsuario = new JButton("Change password");
		menuDatosUsuario.setFont(new Font("Arial", Font.ITALIC, 25));
		menuDatosUsuario.setForeground(Color.darkGray);
		
		menuMiLista = new JButton("My personal list of movies");
		menuMiLista.setFont(new Font("Arial", Font.ITALIC, 25));
		menuMiLista.setForeground(Color.darkGray);
		
		menuMyRentals = new JButton("My history of rentals");
		menuMyRentals.setFont(new Font("Arial", Font.ITALIC, 25));
		menuMyRentals.setForeground(Color.darkGray);
		
		menuFiltro = new JButton("Search...");
		menuFiltro.setFont(new Font("Arial", Font.ITALIC, 25));
		menuFiltro.setForeground(Color.darkGray);
		
		menuCambiarUsuario = new JButton("Log out");
		menuCambiarUsuario.setFont(new Font("Arial", Font.ITALIC, 25));
		menuCambiarUsuario.setForeground(Color.darkGray);
		
		menuCatalogoSeries = new JButton("Series' catalog");
		menuCatalogoSeries.setFont(new Font("Arial", Font.ITALIC, 25));
		menuCatalogoSeries.setForeground(Color.darkGray);
		
		menuCatalogoPelicula = new JButton("Movies' catalog");
		menuCatalogoPelicula.setFont(new Font("Arial", Font.ITALIC, 25));
		menuCatalogoPelicula.setForeground(Color.darkGray);
		
		menuAreaPersonal = new JLabel("==================== Personal");
		menuAreaPersonal.setFont(new Font("Arial", Font.ITALIC, 25));
		menuAreaPersonal.setForeground(Color.blue);
		
		menuAreaPersonal2 = new JLabel("Area ======================");
		menuAreaPersonal2.setFont(new Font("Arial", Font.ITALIC, 25));
		menuAreaPersonal2.setForeground(Color.blue);

		menuAreaDeustoFlix = new JLabel("============== DeustoVideoClub");
		menuAreaDeustoFlix.setFont(new Font("Arial", Font.ITALIC, 25));
		menuAreaDeustoFlix.setForeground(Color.blue);
		
		menuAreaDeustoFlix2 = new JLabel("Area =====================");
		menuAreaDeustoFlix2.setFont(new Font("Arial", Font.ITALIC, 25));
		menuAreaDeustoFlix2.setForeground(Color.blue);
		
		menuHistorial = new JButton("My rating history");
		menuHistorial.setFont(new Font("Arial", Font.ITALIC, 25));
		menuHistorial.setForeground(Color.darkGray);
		
		menuDeustoFlix = new JLabel("DEUSTO VIDEOCLUB");
		menuDeustoFlix.setForeground(Color.white);
		
		menuRecentMovies = new JButton("Recent Movies");
		menuRecentMovies.setFont(new Font("Arial", Font.ITALIC, 25));
		menuRecentMovies.setForeground(Color.darkGray);
		
		//Creamos los paneles
		JPanel panelArriba = new JPanel();
		JPanel panelCentro = new JPanel();
		
		//Panel de Arriba
		panelArriba.setBackground(Color.red);
		menuDeustoFlix.setFont(new Font("Arial", Font.BOLD, 16));
		panelArriba.add(menuDeustoFlix);
		cp.add(panelArriba, BorderLayout.NORTH);
		
		//Panel Central
		panelCentro.setLayout(new GridLayout(8, 2));
		panelCentro.setBackground(new Color(214, 234, 248));
		panelCentro.add(menuAreaPersonal);
		panelCentro.add(menuAreaPersonal2);
		panelCentro.add(menuMyRentals);
		panelCentro.add(menuMiLista);
		panelCentro.add(menuHistorial);
		panelCentro.add(new JLabel());
		panelCentro.add(menuAreaDeustoFlix);
		panelCentro.add(menuAreaDeustoFlix2);
		panelCentro.add(menuCatalogoSeries);
		panelCentro.add(menuCatalogoPelicula);
		panelCentro.add(menuFiltro);
		panelCentro.add(menuRecentMovies);
		panelCentro.add(new JLabel());
		panelCentro.add(new JLabel());
		panelCentro.add(menuDatosUsuario);
		panelCentro.add(menuCambiarUsuario);
		
		cp.add(panelCentro, BorderLayout.CENTER);
		
		//TEST
		System.out.println(user);
		
		//Creamos todos los ActionListeners de esta ventana
		
		//1º) Este boton llama a la VentanaUsuario y desaparece la ventana VentanaMenu existente
		menuCambiarUsuario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 Thread hilo = new LogInWindow();
					hilo.start();
					dispose();
				
				
			}
		});
		//2º) Este boton llama a la VentanaSeries y desaparece la ventana VentanaMenu existente
		menuCatalogoSeries.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		//3º) Este boton llama a la VentanaPeliculas y desaparece la ventana VentanaMenu existente
		menuCatalogoPelicula.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread hilo = new moviesCatalogWindow();
				hilo.start();
				dispose();
				
			}
		});
		//4º) Este boton llama a la VentanaBusqueda y desaparece la ventana VentanaMenu existente
		menuFiltro.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = JOptionPane.showInputDialog("Type in movie title");
				movie = MovieDAO.getInstance().find(title, Movie.ColumnsNameMovie.title);
				if (movie != null) {
					Thread hilo = new viewMovieWindow();
					hilo.start();
					dispose();
				}
			}
		});
		//5º) Este boton llama a la VentanaMisRentals y desaparece la ventana VentanaMenu existente
		menuMyRentals.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 Thread hilo = new RentalsWindow();
				 hilo.start();
				 dispose();
			}
		});
		//7º) Este boton llama a la VentanaMiLista y desaparece la ventana VentanaMenu existente
		menuMiLista.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		menuDatosUsuario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Thread hilo = new InfoWindow();
				 hilo.start();
				 dispose();
			}
		});
		menuHistorial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
	}
	
	public static int getCodUser() {
		return CodUser;
	}

	public static void setCodUser(int codUser) {
		CodUser = codUser;
	}

	class LogInWindow extends Thread{
		public void run() {
			ClientLoginWindow.main(null);
		}
	}
	
	class RentalsWindow extends Thread{ 
		public void run() {
			//MyRentalsWindow.main(null);
			if (myRentalsWindow != null) {
				myRentalsWindow.setVisible(true);
			} else {
				myRentalsWindow = new MyRentalsWindow(user);
				myRentalsWindow.setVisible(true);
			}
		}
	}
	
	class InfoWindow extends Thread{
		public void run() {
			ClientInfoWindow cIW = new ClientInfoWindow(user);
			
		}
	}
	
	class MiListaWindow extends Thread{
		public void run() {
			MyListWindow.main(null);
		}
	}
	
	class moviesCatalogWindow extends Thread{
		public void run() {
			if (moviesWindow != null) {
				moviesWindow.setVisible(true);
			} else {
				moviesWindow = new MoviesWindow(user);
				moviesWindow.setVisible(true);
			}
		}
	}
	
	class viewMovieWindow extends Thread{
		public void run() {
			if (viewMovieWindow != null) {
				viewMovieWindow.setVisible(true);
			} else {
				viewMovieWindow = new ViewMovieWindow(user, movie);
				viewMovieWindow.setVisible(true);
			}
		}
	}
	
	/*public static void main(String[] args) {
		ClientMenuWindow ventana4 = new ClientMenuWindow();
		ventana4.setTitle("DeustoVideoClub - Menu");
		ventana4.setSize(900, 600);
		ventana4.setLocation( 420, 100 );
		ventana4.setVisible( true );
		ventana4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana4.setResizable(false);
	}
	*/
	
}
