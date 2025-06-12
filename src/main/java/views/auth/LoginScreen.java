package views.auth;

import models.Usuario;
import models.dao.UsuarioDAO;
import views.GerenciadorProjetoApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginScreen extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoLogin;

    public LoginScreen() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 250)); // tamanho mínimo
        setPreferredSize(new Dimension(500, 350)); // tamanho preferido
        setLocationRelativeTo(null); // centraliza na tela
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12); // espaço entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelUsuario = new JLabel("Email:");
        campoUsuario = new JTextField(20);

        JLabel labelSenha = new JLabel("Senha:");
        campoSenha = new JPasswordField();

        botaoLogin = new JButton("Entrar");

        gbc.gridx = 0; gbc.gridy = 0;
        add(labelUsuario, gbc);
        gbc.gridx = 1;
        add(campoUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(labelSenha, gbc);
        gbc.gridx = 1;
        add(campoSenha, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(botaoLogin, gbc);

        botaoLogin.addActionListener(this::autenticar);
    }

    private void autenticar(ActionEvent e) {
        String login = campoUsuario.getText();
        String senha = new String(campoSenha.getPassword());

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.autenticar(login, senha);

        System.out.println("Usuário autenticado: " + (usuario != null ? usuario.getNome() : "null"));


        if (usuario != null) {
            dispose();
            new GerenciadorProjetoApp(usuario).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}
