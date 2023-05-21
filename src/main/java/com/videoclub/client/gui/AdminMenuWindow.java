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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.client.gui.ClientMenuWindow.LogInWindow;
import com.videoclub.client.gui.ClientMenuWindow.moviesCatalogWindow;
import com.videoclub.client.gui.ClientMenuWindow.viewMovieWindow;
import com.videoclub.dao.MovieDAO;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.User;


/**
 * GUI window for the admin menu.
 */
public class AdminMenuWindow extends JFrame {

	private static final long serialVersionUID = -1757340437087937985L;

	private JButton menuUsuarios;
	private JButton menuBuscar;
	private JButton menuCambiarUsuario;
	private JButton menuEditarPeli;
	private JButton menuCatalogoPelicula;
	private JButton menuAnyadirPeli;
	private JButton menuRentalMovies;

	private JLabel menuDeustoFlix;

	private EditUsersWindow editUserWindow;
	private RentalWindow rentalWindow;
	private MoviesWindow moviesWindow;
	private MoviesWindowAdmin moviesWindowAdmin;
	private EditMoviesWindow editMoviesWindow;
	private NewMovieWindow newMovieWindow;
	private ViewMovieAdminWindow viewMovieAdminWindow;
	private User user;
	private Movie movie;
	
	protected static final Logger logger = LogManager.getLogger();

	/**
	 * Creates an instance of AdminMenuWindow.
	 *
	 * @param user The logged-in user.
	 */
	public AdminMenuWindow() {
		
		

		this.setTitle("DeustoVideoClub - Admin - Menu");
		this.setSize(900, 600);
		this.setLocation(420, 100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		// Create the container to hold everything
		Container cp = this.getContentPane();

		// Create components with their specifications
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

		menuRentalMovies = new JButton(InternationalizationText.getString("show_rental_history"));
		menuRentalMovies.setFont(new Font("Arial", Font.ITALIC, 25));
		menuRentalMovies.setForeground(Color.darkGray);

		// Create panels
		JPanel panelArriba = new JPanel();
		JPanel panelCentro = new JPanel();

		// Top Panel
		panelArriba.setBackground(Color.red);
		menuDeustoFlix.setFont(new Font("Arial", Font.BOLD, 16));
		panelArriba.add(menuDeustoFlix);
		cp.add(panelArriba, BorderLayout.NORTH);

		// Center Panel
		panelCentro.setLayout(new GridLayout(8, 2));
		panelCentro.setBackground(new Color(214, 234, 248));
		panelCentro.add(new JLabel());
		panelCentro.add(new JLabel());
		panelCentro.add(menuUsuarios);
		panelCentro.add(menuRentalMovies);
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

		// Create ActionListeners for the window buttons
		menuUsuarios.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread hilo = new UsersWindow();
				hilo.start();
				dispose();
			}
		});

		menuRentalMovies.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread hilo = new RentalsWindow();
				hilo.start();
				dispose();
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
				String title = JOptionPane.showInputDialog(InternationalizationText.getString("typeinmovie"));
				movie = MovieDAO.getInstance().find(title, Movie.ColumnsNameMovie.title);
				if (movie != null) {
					Thread hilo = new viewMovieAdminWindow();
					hilo.start();
					dispose();
				}
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

	/**
	 * Thread class for the login window.
	 */
	class LogInWindow extends Thread {
		public void run() {
			ClientLoginWindow.main(null);
		}
	}
	class viewMovieAdminWindow extends Thread {
		public void run() {
			if (viewMovieAdminWindow != null) {
				viewMovieAdminWindow.setVisible(true);
			} else {
				viewMovieAdminWindow = new ViewMovieAdminWindow(user, movie);
				viewMovieAdminWindow.setVisible(true);
			}
		}
	}
	
	class UsersWindow extends Thread {
		public void run() {
			if (editUserWindow != null) {
				editUserWindow.setVisible(true);
			} else {
				editUserWindow = new EditUsersWindow();
				editUserWindow.setVisible(true);
			}
		}
	}
	
	
	class RentalsWindow extends Thread {
		public void run() {
			if (rentalWindow != null) {
				rentalWindow.setVisible(true);
			} else {
				rentalWindow = new RentalWindow();
				rentalWindow.setVisible(true);
			}
		}
	}
	
	/**
	 * Thread class for the movies catalog window.
	 */
	class moviesCatalogWindow extends Thread {
		public void run() {
			if (moviesWindowAdmin != null) {
				moviesWindowAdmin.setVisible(true);
			} else {
				moviesWindowAdmin = new MoviesWindowAdmin();
				moviesWindowAdmin.setVisible(true);
			}
		}
	}

	/**
	 * Thread class for the movie window.
	 */
	class movieWindow extends Thread {
		public void run() {
			if (newMovieWindow != null) {
				newMovieWindow.setVisible(true);
			} else {
				newMovieWindow = new NewMovieWindow();
				newMovieWindow.setVisible(true);
			}
		}
	}

	/**
	 * Thread class for the edit window.
	 */
	class editWindow extends Thread {
		public void run() {
			if (editMoviesWindow != null) {
				editMoviesWindow.setVisible(true);
			} else {
				editMoviesWindow = new EditMoviesWindow();
				editMoviesWindow.setVisible(true);
			}
		}
	}
}
