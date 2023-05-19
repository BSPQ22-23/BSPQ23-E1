package com.videoclub.client.gui;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.videoclub.Internationalization.InternationalizationText;
import com.videoclub.encrypt.PasswordEncrypt;
import com.videoclub.pojo.User;


/**
 * The ClientInfoWindow class represents a GUI window where the user can view and edit their personal information.
 */
public class ClientInfoWindow extends JFrame {

    private JPanel contentPane;
    private JPanel panelSouth;
    private JPanel panelCentre;
    private JLabel lblOldPass;
    private JTextField txtOldPass;
    private JLabel lblNewPass;
    private JTextField textPass;
    private JButton btnSave;
    private JButton btnBack;
    protected static final Logger logger = LogManager.getLogger();
    private static final String SERVER_ENDPOINT = "http://localhost:8080/webapi";
    private static final String USERS_RESOURCE = "users";

    /**
     * Create the frame.
     *
     * @param user The User object representing the logged-in user.
     */
    public ClientInfoWindow(User user) {
        // Frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                    user.setPassword(PasswordEncrypt.encryptPassword(textPass.getText()));

                    Client client = ClientBuilder.newClient();
                    WebTarget target = client.target(SERVER_ENDPOINT);

                    Response response = target.path(USERS_RESOURCE)
                            .request(MediaType.APPLICATION_JSON)
                            .post(javax.ws.rs.client.Entity.entity(user, MediaType.APPLICATION_JSON));

                    if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                        logger.info(InternationalizationText.getString("user_upd_correct"));
                    } else {
                        logger.info(InternationalizationText.getString("user_upd_fail"));
                    }

                    response.close();
                    client.close();
                }

                // Return to the menu window
                Thread hilo = new MenuWindow();
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
            ClientMenuWindow.main(null);
        }
    }
}
