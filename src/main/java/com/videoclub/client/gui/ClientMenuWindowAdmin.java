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




public class ClientMenuWindowAdmin extends JFrame{

	private static final long serialVersionUID = -1757340437087937985L;
	
	private JButton menuMiLista;
	private JButton menuSeriesV;
	private JButton menuFiltro;
	private JButton menuCambiarUsuario;
	private JButton menuCatalogoSeries;
	private JButton menuCatalogoPelicula;
	private JButton menuDatosUsuario;
	private JButton menuHistorial;
	private JButton menuRecentMovies;
	
	private JLabel menuDeustoFlix;
	
	public ClientMenuWindowAdmin() {
		//Creamos el contenedor donde vamos a colocar todo
		Container cp = this.getContentPane();
		
		//Creamos los componentes con sus especificaciones
		menuDatosUsuario = new JButton("Change password");
		menuDatosUsuario.setFont(new Font("Arial", Font.ITALIC, 25));
		menuDatosUsuario.setForeground(Color.darkGray);
		
		menuMiLista = new JButton("");
		menuMiLista.setFont(new Font("Arial", Font.ITALIC, 25));
		menuMiLista.setForeground(Color.darkGray);
		
		menuSeriesV = new JButton("Manage videoclub users");
		menuSeriesV.setFont(new Font("Arial", Font.ITALIC, 25));
		menuSeriesV.setForeground(Color.darkGray);
		
		menuFiltro = new JButton("Search...");
		menuFiltro.setFont(new Font("Arial", Font.ITALIC, 25));
		menuFiltro.setForeground(Color.darkGray);
		
		menuCambiarUsuario = new JButton("Log out");
		menuCambiarUsuario.setFont(new Font("Arial", Font.ITALIC, 25));
		menuCambiarUsuario.setForeground(Color.darkGray);
		
		menuCatalogoSeries = new JButton("Update catalog");
		menuCatalogoSeries.setFont(new Font("Arial", Font.ITALIC, 25));
		menuCatalogoSeries.setForeground(Color.darkGray);
		
		menuCatalogoPelicula = new JButton("Add new serie");
		menuCatalogoPelicula.setFont(new Font("Arial", Font.ITALIC, 25));
		menuCatalogoPelicula.setForeground(Color.darkGray);
		
		menuHistorial = new JButton("Add new movie");
		menuHistorial.setFont(new Font("Arial", Font.ITALIC, 25));
		menuHistorial.setForeground(Color.darkGray);
		
		menuDeustoFlix = new JLabel("DEUSTO VIDEOCLUB - Admin Window");
		menuDeustoFlix.setForeground(Color.white);
		
		menuRecentMovies = new JButton("Show users rental history");
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
		panelCentro.add(menuSeriesV);
		panelCentro.add(menuMiLista);
		panelCentro.add(menuHistorial);
		panelCentro.add(new JLabel());
		panelCentro.add(new JLabel());
		panelCentro.add(new JLabel());
		panelCentro.add(menuCatalogoSeries);
		panelCentro.add(menuCatalogoPelicula);
		panelCentro.add(menuFiltro);
		panelCentro.add(menuRecentMovies);
		panelCentro.add(new JLabel());
		panelCentro.add(new JLabel());
		panelCentro.add(menuDatosUsuario);
		panelCentro.add(menuCambiarUsuario);
		
		cp.add(panelCentro, BorderLayout.CENTER);
		
		//Creamos todos los ActionListeners de esta ventana
		
		//1º) Este boton llama a la VentanaUsuario y desaparece la ventana VentanaMenu existente
		menuCambiarUsuario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
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
				
				
			}
		});
		//4º) Este boton llama a la VentanaBusqueda y desaparece la ventana VentanaMenu existente
		menuFiltro.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		//5º) Este boton llama a la VentanaMisSeries y desaparece la ventana VentanaMenu existente
		menuSeriesV.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
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
				
				
			}
		});
		menuHistorial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
	}
	public static void main(String[] args) {
		ClientMenuWindowAdmin ventana4 = new ClientMenuWindowAdmin();
		ventana4.setTitle("DeustoVideoClub - Admin - Menu");
		ventana4.setSize(900, 600);
		ventana4.setLocation( 420, 100 );
		ventana4.setVisible( true );
		ventana4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana4.setResizable(false);
	}
	
	
}
