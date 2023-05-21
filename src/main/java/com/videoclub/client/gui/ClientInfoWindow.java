package com.videoclub.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.client.ConnectionToServer;
import com.videoclub.encrypt.PasswordEncrypt;
import com.videoclub.pojo.User;


/**
 * The ClientInfoWindow class represents a GUI window where the user can view and edit their personal information.
 */
public class ClientInfoWindow extends JFrame {

    
	private static final long serialVersionUID = 2475862372385609472L;
	private JPanel contentPane;
    private JPanel panelSouth;
    private JPanel panelCentre;
    private JLabel lblNewPass;
    private JTextField textPass;
    private JButton btnSave;
    private JButton btnBack;
    protected static final Logger logger = LogManager.getLogger();
    private ClientMenuWindow clientMenuWindow;
    private User user;
    /**
     * Create the frame.
     *
     * @param user The User object representing the logged-in user.
     */
    public ClientInfoWindow(User user) {
    	this.user = user;
    	
    	ConnectionToServer cts = new ConnectionToServer();
        // Frame settings
        setBounds(100, 100, 450, 300);
        setLocationRelativeTo(null);

        // Content pane
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // South panel
        panelSouth = new JPanel();
        contentPane.add(panelSouth, BorderLayout.SOUTH);

        btnSave = new JButton(InternationalizationText.getString("save"));
        panelSouth.add(btnSave);
        btnBack = new JButton(InternationalizationText.getString("back"));
        panelSouth.add(btnBack);

        // Center panel
        panelCentre = new JPanel();
        contentPane.add(panelCentre, BorderLayout.CENTER);
        panelCentre.setLayout(new GridLayout(0, 2, 0, 0));

        // TODO: Add more personal info fields

        lblNewPass = new JLabel(InternationalizationText.getString("new_pass"));
        panelCentre.add(lblNewPass);

        textPass = new JPasswordField();
        panelCentre.add(textPass);
        textPass.setColumns(10);

        setVisible(true);

        // Save button action listener
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Confirm the action
                int choice = JOptionPane.showConfirmDialog(null, InternationalizationText.getString("sure_comprob"), "Confirmation", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    // Update the user's password
                	String passwordSaved = user.getPassword();
                	user.setPassword(PasswordEncrypt.encryptPassword(textPass.getText()));
                	boolean correctUpdate = cts.changePasswordUserClient(user);
                	if(correctUpdate) {
                		JOptionPane.showMessageDialog(null, "Password changed succesfully.");
                		textPass.setText("");
                	}else {
                		user.setPassword(passwordSaved);
                	}

                }
                Thread hilo = new MenuWindow();
                ClientMenuWindow.setCodUser(user.getCode());
                hilo.start();
                dispose();
            }
        });

        // Back button action listener
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Return to the menu window
                Thread hilo = new MenuWindow();
                ClientMenuWindow.setCodUser(user.getCode());
                hilo.start();
                dispose();
            }
        });
    }

    /**
     * Thread class for the menu window.
     */
    class MenuWindow extends Thread {
        public void run() {
        	if(clientMenuWindow != null) {
                clientMenuWindow.setVisible(true);
        	}else {
        		clientMenuWindow = new ClientMenuWindow(user);
        		clientMenuWindow.setVisible(true);
        	}
            
        }
    }
}
