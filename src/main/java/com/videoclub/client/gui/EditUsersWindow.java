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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.videoclub.client.ConnectionToServer;
import com.videoclub.client.gui.NewMovieWindow.AdminWindow;
import com.videoclub.dao.MovieDAO;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.User;

public class EditUsersWindow extends JFrame {
	

	private static final long serialVersionUID = -7282072359640068019L;
	private JButton exit;
	private JButton save;
	private JButton delete;
	
	
	private JTextField username;
	private JPasswordField password;
	private JTextField email;
	private JTextField name;
	private JTextField surname;
	private JTextField type;
	private JTextField empty;
	
	
	JList<String> users;
	
	
	private HashMap<String,User> grupoUsers; 
	private AdminMenuWindow adminMenuWindow;
	
	

	private static final String SERVER_ENDPOINT = "http://localhost:8080/webapi";
    private static final String MOVIES_RESOURCE ="movies";
    protected static final Logger logger = LogManager.getLogger();
	
    
	public EditUsersWindow()
	{
		
		
		this.setSize(900, 600);
		this.setLocation( 420, 100 );
		
		Container cp = this.getContentPane();
		ConnectionToServer cts = new ConnectionToServer();
		
		exit = new JButton("Back");
		delete = new JButton("Delete");
		delete.setEnabled(false);
		
		username = new JTextField(20);
		username.setEnabled(false);
		password = new JPasswordField(20);
		password.setEnabled(false);
		email = new JTextField(20);
		email.setEnabled(false);
		name = new JTextField(20);
		name.setEnabled(false);
		surname = new JTextField(20);
		surname.setEnabled(false);
		type = new JTextField(20);
		type.setEnabled(false);
		
		empty = new JTextField();
		empty.setVisible(false);
		

		
		
		
		users = new JList<String>();
		users.setFixedCellWidth(200);
		users.setFixedCellHeight(50);
		users.setModel(cargarUsuarios());
		//movies.setBackground(new Color(60,141,207));
		DefaultListModel<String> lm = new DefaultListModel<String>();
		
		JScrollPane spIzquierda = new JScrollPane(users);
		
		JPanel panelArriba = new JPanel();
		JPanel panelIzquierda = new JPanel();
		JPanel panelCentro = new JPanel();
		JPanel panelAbajo = new JPanel();
		
		
		panelIzquierda.add(spIzquierda);
		
		
		panelArriba.setBackground(Color.red);

		
		
		panelCentro.setLayout(new GridLayout(8,2));
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"Username: ",username);
		posicionaLinea(panelCentro,"Password: ",password);
		posicionaLinea(panelCentro,"Email: ",email);
		posicionaLinea(panelCentro,"Name: ",name);
		posicionaLinea(panelCentro,"Surname: ",surname);
		posicionaLinea(panelCentro,"Type: ",type);
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"",empty);
		posicionaLinea(panelCentro,"",empty);
		

		panelAbajo.add(delete);
		panelAbajo.add(exit);
		
		cp.add(panelIzquierda, BorderLayout.WEST);
		cp.add(panelArriba, BorderLayout.NORTH);
		cp.add(panelCentro, BorderLayout.CENTER);
		cp.add(panelAbajo, BorderLayout.SOUTH);
		
		

	
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				User u = grupoUsers.get(users.getSelectedValue());
				
				boolean hasDeleted = cts.deleteUserClient(u);
				if(hasDeleted) {
					grupoUsers.remove(users.getSelectedValue());
	                ((DefaultListModel<String>)users.getModel()).removeElement(u.getUsername());
				}
            	
				delete.setEnabled(false);
				username.setText("");
				password.setText("");
				email.setText("");
				name.setText("");
				surname.setText("");
				type.setText("");
				
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
		
		users.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (users.getSelectedValue()!=null)
				{
					cargaDatosUser(grupoUsers.get(users.getSelectedValue()));
					delete.setEnabled(true);
					
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
	
	public DefaultListModel<String> cargarUsuarios()
	{
		
		ConnectionToServer cts = new ConnectionToServer();
		HashMap<String,User> grupo = new HashMap<String,User>();
		List<User> listUsers = cts.takeUserListClient();

		DefaultListModel<String> dl = new DefaultListModel<String>();
		String s;
		
		if(listUsers != null) {
			for(User u: listUsers)
			{
				grupo.put(u.getUsername(), u);
				s = u.getUsername();
				
				dl.addElement(s);
			}
			grupoUsers = grupo;
			return dl;
		}
		return null;
		
	}
	
	public  void cargaDatosUser(User u ) {
		if (u != null) {
			username.setText(u.getUsername());
			password.setText(u.getPassword());
			email.setText(u.getEmail());
			name.setText(u.getName());
			surname.setText(u.getSurname());
			type.setText(u.getType().toString());
		}
	}
	class AdminWindow extends Thread{
		public void run() {
			if (adminMenuWindow != null) {
				adminMenuWindow.setVisible(true);
			} else {
				adminMenuWindow = new AdminMenuWindow();
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



