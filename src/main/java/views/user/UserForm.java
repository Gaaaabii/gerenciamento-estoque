package views.user;

import models.Usuario;
import models.dao.UsuarioDAO;

import javax.swing.*;
import java.awt.*;

public class UserForm extends JPanel {
    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JButton salvarButton;

    private Usuario usuarioAtual;
    private Runnable aoSalvarCallback;

    public UserForm() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nomeLabel = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nomeLabel, gbc);

        nomeField = new JTextField(20);
        gbc.gridx = 1;
        add(nomeField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);

        JLabel senhaLabel = new JLabel("Senha:");
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(senhaLabel, gbc);

        senhaField = new JPasswordField(20);
        gbc.gridx = 1;
        add(senhaField, gbc);

        salvarButton = new JButton("Salvar");
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(salvarButton, gbc);

        salvarButton.addActionListener(e -> salvarUsuario());
    }

    public void setAoSalvarCallback(Runnable callback) {
        this.aoSalvarCallback = callback;
    }

    public void limparCampos() {
        usuarioAtual = null;
        nomeField.setText("");
        emailField.setText("");
        senhaField.setText("");
    }

    public void preencherCampos(Usuario usuario) {
        this.usuarioAtual = usuario;
        nomeField.setText(usuario.getNome());
        emailField.setText(usuario.getEmail());
        senhaField.setText(usuario.getSenha());
    }

    private void salvarUsuario() {
        UsuarioDAO dao = new UsuarioDAO();

        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();
        String senha = new String(senhaField.getPassword()).trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.");
            return;
        }

        if (senha.length() < 6) {
            JOptionPane.showMessageDialog(this, "A senha deve conter pelo menos 6 caracteres.");
            return;
        }

        // Validação de e-mail único (somente para novo usuário ou troca de e-mail)
        Usuario existente = dao.buscarPorEmail(email);
        if (existente != null && (usuarioAtual == null || existente.getId() != usuarioAtual.getId())) {
            JOptionPane.showMessageDialog(this, "Este e-mail já está cadastrado.");
            return;
        }

        if (usuarioAtual == null) {
            usuarioAtual = new Usuario();
        }

        usuarioAtual.setNome(nome);
        usuarioAtual.setEmail(email);
        usuarioAtual.setSenha(senha);

        dao.salvarOuAtualizar(usuarioAtual);

        JOptionPane.showMessageDialog(this, "Usuário salvo com sucesso!");

        if (aoSalvarCallback != null) {
            aoSalvarCallback.run();
        }
    }
}
