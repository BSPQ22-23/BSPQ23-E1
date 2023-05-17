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
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.client.gui.ClientMenuWindow.LogInWindow;
import com.videoclub.client.gui.ClientMenuWindow.moviesCatalogWindow;
import com.videoclub.pojo.User;




public class AdminMenuWindow extends JFrame{

	private static final long serialVersionUID = -1757340437087937985L;
	

	private JButton menuUsuarios;
	private JButton menuBuscar;
	private JButton menuCambiarUsuario;
	private JButton menuEditarPeli;
	private JButton menuCatalogoPelicula;
	private JButton menuAnyadirPeli;
	private JButton menuRecentMovies;
	
	private JLabel menuDeustoFlix;
	
	private MoviesWindow moviesWindow;
	private EditMoviesWindow editMoviesWindow;
	private NewMovieWindow newMovieWindow;
	private User user;
	protected static final Logger logger = LogManager.getLogger();
	
	public AdminMenuWindow(User user) {
		
		this.user = user;
		
		this.setTitle("DeustoVideoClub - Admin - Menu");
		this.setSize(900, 600);
		this.setLocation( 420, 100 );
		this.setVisible( true );
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		//Creamos el contenedor donde vamos a colocar todo
		Container cp = this.getContentPane();
		
		//Creamos los componentes con sus especificaciones

		
		menuUsuarios = new JButton(InternationalizationText.getString("manage_users"));
		menuUsuarios.setFont(new Font("Arial", Font.ITALIC, 25));
		menuUsuarios.setForeground(Color.darkGray);
		
		menuBuscar = new JButton(InternationalizationText.getString("search"));
		menuBuscar.setFont(new Font("Arial", Font.ITALIC, 25));
		menuBuscar.setForeground(Color.darkGray);
		
		menuCambiarUsuario = new JButton(InternationalizationText.getString("log_out"));
		menuCambiarUsuario.setFont(new Font("Arial", Font.ITALIC, 25));
		menuCambiarUsuario.setForeground(Color.darkGray);
		
		menuEditarPeli = new JButton(InternationalizationText.getString("update_catalog"));
		menuEditarPeli.setFont(new Font("Arial", Font.ITALIC, 25));
		menuEditarPeli.setForeground(Color.darkGray);
		
		menuCatalogoPelicula = new JButton(InternationalizationText.getString("movies_total"));
		menuCatalogoPelicula.setFont(new Font("Arial", Font.ITALIC, 25));
		menuCatalogoPelicula.setForeground(Color.darkGray);
		
		menuAnyadirPeli = new JButton(InternationalizationText.getString("new_movie"));
		menuAnyadirPeli.setFont(new Font("Arial", Font.ITALIC, 25));
		menuAnyadirPeli.setForeground(Color.darkGray);
		
		menuDeustoFlix = new JLabel(InternationalizationText.getString("deusto_admin_win"));
		menuDeustoFlix.setForeground(Color.white);
		
		menuRecentMovies = new JButton(InternationalizationText.getString("show_rental_history"));
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
		panelCentro.add(new JLabel());
		panelCentro.add(new JLabel());
		panelCentro.add(menuUsuarios);
		panelCentro.add(menuRecentMovies);
		panelCentro.add(new JLabel());
		panelCentro.add(new JLabel());
		panelCentro.add(menuAnyadirPeli);
		panelCentro.add(menuCatalogoPelicula);
		panelCentro.add(menuEditarPeli);
		panelCentro.add(menuBuscar);
		panelCentro.add(new JLabel());
		panelCentro.add(new JLabel());
		panelCentro.add(new JLabel());
		panelCentro.add(new JLabel());
		panelCentro.add(new JLabel());	
		panelCentro.add(menuCambiarUsuario);
		
		cp.add(panelCentro, BorderLayout.CENTER);
		
		//Creamos todos los ActionListeners de esta ventana
		menuUsuarios.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						//TODO
					}
				});
		
		menuRecentMovies.addActionListener(new ActionListener() {
			
					@Override
					public void actionPerformed(ActionEvent e) {
						//TODO
					}
				});
		menuAnyadirPeli.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						Thread hilo = new movieWindow();
						hilo.start();
						dispose();
						
					}
				});
		menuCatalogoPelicula.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread hilo = new moviesCatalogWindow();
				hilo.start();
				dispose();
				
			}
		});
		menuEditarPeli.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						Thread hilo = new editWindow();
						hilo.start();
						dispose();
						
					}
				});
		menuBuscar.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						//TODO
					}
				});
	
		menuCambiarUsuario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 Thread hilo = new LogInWindow();
					hilo.start();
					dispose();
				
			}
		});
		
	}
	class LogInWindow extends Thread{
		public void run() {
			ClientLoginWindow.main(null);
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
	
	class movieWindow extends Thread{
		public void run() {
			if (newMovieWindow != null) {
				newMovieWindow.setVisible(true);
			} else {
				newMovieWindow = new NewMovieWindow(user);
				newMovieWindow.setVisible(true);
			}
		}
	}
	
	class editWindow extends Thread{
		public void run() {
			if (editMoviesWindow != null) {
				editMoviesWindow.setVisible(true);
			} else {
				editMoviesWindow = new EditMoviesWindow(user);
				editMoviesWindow.setVisible(true);
			}		}
	}
	

	
	/*public static void main(String[] args) {
		AdminMenuWindow ventana4 = new AdminMenuWindow();
		ventana4.setTitle("DeustoVideoClub - Admin - Menu");
		ventana4.setSize(900, 600);
		ventana4.setLocation( 420, 100 );
		ventana4.setVisible( true );
		ventana4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana4.setResizable(false);
	}*/
	
	
}
