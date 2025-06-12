package views;

import views.estoque.EstoqueList;
import views.estoque.EstoqueForm;
import views.user.UserForm;
import views.user.UserList;
import models.Estoque;
import models.Usuario;

import javax.swing.*;
import java.awt.*;

public class GerenciadorProjetoApp extends JFrame {
    private static final String EMPTY_SCREEN = "EMPTY_SCREEN";
    private static final String USER_LIST_SCREEN = "USER_LIST_SCREEN";
    private static final String USER_FORM_SCREEN = "USER_FORM_SCREEN";
    private static final String ESTOQUE_LIST_SCREEN = "ESTOQUE_LIST_SCREEN";
    private static final String ESTOQUE_FORM_SCREEN = "ESTOQUE_FORM_SCREEN";

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private EstoqueForm estoqueForm;
    private EstoqueList estoqueList;
    private UserList userList;
    private UserForm userForm;

    public GerenciadorProjetoApp() {
        setTitle("Gerenciador de Projetos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.add(new JLabel("Bem-vindo! Use o menu para navegar!", SwingConstants.CENTER), BorderLayout.CENTER);
        mainPanel.add(emptyPanel, EMPTY_SCREEN);

        userList = new UserList(usuario -> {
            userForm.preencherCampos(usuario);
            cardLayout.show(mainPanel, USER_FORM_SCREEN);
        });

        userForm = new UserForm();
        userForm.setAoSalvarCallback(() -> {
            userList.atualizarTabela(); // Recarrega os dados na tabela
            cardLayout.show(mainPanel, USER_LIST_SCREEN); // Redireciona para a listagem
        });

        mainPanel.add(userList, USER_LIST_SCREEN);
        mainPanel.add(userForm, USER_FORM_SCREEN);

        estoqueList = new EstoqueList(this::abrirFormularioEdicao);
        mainPanel.add(estoqueList, ESTOQUE_LIST_SCREEN);

        estoqueForm = new EstoqueForm();
        estoqueForm.setAoSalvarCallback(() -> {
            estoqueList.atualizarTabela();
            cardLayout.show(mainPanel, ESTOQUE_LIST_SCREEN);
        });
        mainPanel.add(estoqueForm, ESTOQUE_FORM_SCREEN);

        JMenu menu = new JMenu("Menu");
        JMenuItem novoUsuarioItem = new JMenuItem("Cadastro de Usuário");
        JMenuItem listUsersItem = new JMenuItem("Listar Usuários");
        JMenuItem listEstoqueItem = new JMenuItem("Listar Estoque");
        JMenuItem cadastrarEstoqueItem = new JMenuItem("Cadastrar Estoque");
        JMenuItem exitItem = new JMenuItem("Sair");

        menu.add(listUsersItem);
        menu.add(novoUsuarioItem);
        menu.add(listEstoqueItem);
        menu.add(cadastrarEstoqueItem);
        menu.add(exitItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar(menuBar);

        listUsersItem.addActionListener(e -> cardLayout.show(mainPanel, USER_LIST_SCREEN));

        novoUsuarioItem.addActionListener(e -> {
            userForm.limparCampos();
            cardLayout.show(mainPanel, USER_FORM_SCREEN);
        });

        listEstoqueItem.addActionListener(e -> {
            estoqueList.atualizarTabela();
            cardLayout.show(mainPanel, ESTOQUE_LIST_SCREEN);
        });

        cadastrarEstoqueItem.addActionListener(e -> {
            estoqueForm.limparCampos();
            cardLayout.show(mainPanel, ESTOQUE_FORM_SCREEN);
        });

        exitItem.addActionListener(e -> dispose());

        add(mainPanel);
    }

    public void abrirFormularioEdicao(Estoque estoque) {
        estoqueForm.preencherCampos(estoque);
        cardLayout.show(mainPanel, ESTOQUE_FORM_SCREEN);
    }

    private Usuario usuarioLogado;

    public GerenciadorProjetoApp(Usuario usuario) {
        this();
        this.usuarioLogado = usuario;
        setTitle("Bem-vindo, " + usuario.getNome());
    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "2.0");
        SwingUtilities.invokeLater(() -> new GerenciadorProjetoApp().setVisible(true));
    }
    public void abrirFormularioUsuario(Usuario usuario) {
        userForm.preencherCampos(usuario);
        cardLayout.show(mainPanel, USER_FORM_SCREEN);

    }
}
