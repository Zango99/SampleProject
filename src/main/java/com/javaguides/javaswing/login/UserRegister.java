package com.javaguides.javaswing.login;

import com.mysql.cj.util.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserRegister extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;
    private JPasswordField password;
    private JButton registerButton;
    private JButton cancelButton;
    private JLabel label;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserRegister frame = new UserRegister();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public UserRegister() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 500);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Register");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lblNewLabel.setBounds(423, 13, 273, 30);
        contentPane.add(lblNewLabel);

        JLabel lblUsername = new JLabel("First Name");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lblUsername.setBounds(250, 50, 193, 30);
        contentPane.add(lblUsername);

        firstName = new JTextField();
        firstName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        firstName.setBounds(481, 50, 250, 30);
        contentPane.add(firstName);
        firstName.setColumns(10);

        JLabel lblPassword = new JLabel("Last Name");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lblPassword.setBounds(250, 90, 193, 30);
        contentPane.add(lblPassword);

        lastName = new JTextField();
        lastName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lastName.setBounds(481, 90, 250, 30);
        contentPane.add(lastName);
        lastName.setColumns(10);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setForeground(Color.BLACK);
        lblEmail.setBackground(Color.CYAN);
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lblEmail.setBounds(250, 140, 193, 30);
        contentPane.add(lblEmail);

        email = new JTextField();
        email.setFont(new Font("Tahoma", Font.PLAIN, 20));
        email.setBounds(481, 140, 250, 30);
        contentPane.add(email);
        email.setColumns(10);

        JLabel lblPass = new JLabel("Password");
        lblPass.setForeground(Color.BLACK);
        lblPass.setBackground(Color.CYAN);
        lblPass.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lblPass.setBounds(250, 190, 193, 30);
        contentPane.add(lblPass);

        password = new JPasswordField();
        password.setFont(new Font("Tahoma", Font.PLAIN, 20));
        password.setBounds(481, 190, 250, 30);
        contentPane.add(password);


        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        registerButton.setBounds(245, 250, 162, 50);
        registerButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String fname = firstName.getText();
                String lname = lastName.getText();
                String uemail = email.getText();
                String pass = password.getText();
                if(StringUtils.isNullOrEmpty(fname) || StringUtils.isNullOrEmpty(lname) || StringUtils.isNullOrEmpty(uemail) || StringUtils.isNullOrEmpty(pass)){
                    JOptionPane.showMessageDialog(registerButton, "Please enter all fields");
                    return;
                }

                PreparedStatement st = null;
                ResultSet rs = null;
                try {
                    st = (PreparedStatement) DbUtil.getConnection().prepareStatement("SELECT * FROM student WHERE email=?");
                    st.setString(1, uemail);
                    rs = st.executeQuery();
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(registerButton, "Email is already register");
                    } else {
                        System.out.println(fname);
                        System.out.println(lname);
                        System.out.println(uemail);
                        System.out.println(pass);
                        String insert = "INSERT INTO student (firstname, lastname, email, password) VALUES (?,?,?,?)";
                        st = (PreparedStatement) DbUtil.getConnection().prepareStatement(insert);
                        st.setString(1, fname);
                        st.setString(2, lname);
                        st.setString(3, uemail);
                        st.setString(4, pass);
                        if (st.executeUpdate() > 0) {
                            dispose();
                            UserLogin ah = new UserLogin();
                            ah.setVisible(true);
                            JOptionPane.showMessageDialog(registerButton, "You have successfully register, please log in to continue");
                        } else {
                            JOptionPane.showMessageDialog(registerButton, "Something went wrong");
                        }
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    try {
                        if (rs != null && !rs.isClosed()) {
                            rs.close();
                        }
                        if (st != null && !st.isClosed()) {
                            st.close();
                        }
                        DbUtil.closeConnection();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        contentPane.add(registerButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        cancelButton.setBounds(545, 250, 162, 50);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UserLogin ah = new UserLogin();
                ah.setVisible(true);
            }
        });

        contentPane.add(cancelButton);
        label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        contentPane.add(label);
    }
}