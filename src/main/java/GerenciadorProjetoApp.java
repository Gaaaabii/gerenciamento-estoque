import views.estoque.EstoqueList;
import views.estoque.EstoqueForm;
import views.utils.ButtonEditor;
import views.user.UserList;
import models.Estoque;

import javax.swing.*;
import java.awt.*;

public class GerenciadorProjetoApp extends JFrame {

    private static final String EMPTY_SCREEN = "EMPTY_SCREEN";
    private static final String USER_LIST_SCREEN = "USER_LIST_SCREEN";
    private static final String ESTOQUE_LIST_SCREEN = "ESTOQUE_LIST_SCREEN";
    private static final String ESTOQUE_FORM_SCREEN = "ESTOQUE_FORM_SCREEN";
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private EstoqueForm estoqueForm;
    private EstoqueList estoqueList;


    public GerenciadorProjetoApp(){
        setTitle("Gerenciador de Projetos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Empty Panel
        JPanel emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.add(
                new JLabel("Bem-vindo! Use o menu para navegar!",
                        SwingConstants.CENTER), BorderLayout.CENTER);
        mainPanel.add(emptyPanel, EMPTY_SCREEN);

        // Screen User List
        UserList userList = new UserList();
        mainPanel.add(userList, USER_LIST_SCREEN);

        estoqueList = new EstoqueList(this::abrirFormularioEdicao);
        mainPanel.add(estoqueList, ESTOQUE_LIST_SCREEN);

        estoqueForm = new EstoqueForm();

        estoqueForm.setAoSalvarCallback(() -> {
            estoqueList.atualizarTabela();
            cardLayout.show(mainPanel, ESTOQUE_LIST_SCREEN);
        });

        mainPanel.add(estoqueForm, ESTOQUE_FORM_SCREEN);

        JMenu menu = new JMenu("Menu");
        JMenuItem listUsersItem = new JMenuItem("Listar Usuários");
        JMenuItem listEstoqueItem = new JMenuItem("Listar Estoque");
        JMenuItem cadastrarEstoqueItem  = new JMenuItem("Cadastrar Estoque");
        JMenuItem exitItem = new JMenuItem("Sair");

        menu.add(listUsersItem);
        menu.add(listEstoqueItem);
        menu.add(cadastrarEstoqueItem);
        menu.add(exitItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);

        setJMenuBar(menuBar);

        listUsersItem.addActionListener(e -> {
            cardLayout.show(mainPanel, USER_LIST_SCREEN);
        });

        listEstoqueItem.addActionListener(e -> {
            estoqueList.atualizarTabela();
            cardLayout.show(mainPanel, ESTOQUE_LIST_SCREEN);
        });

        cadastrarEstoqueItem.addActionListener(e -> {
            estoqueForm.limparCampos();
            cardLayout.show(mainPanel, ESTOQUE_FORM_SCREEN);
        });


        exitItem.addActionListener(event -> {
            dispose();
        });

        add(mainPanel);
    }

    public void abrirFormularioEdicao(Estoque estoque) {
        estoqueForm.preencherCampos(estoque);
        cardLayout.show(mainPanel, "ESTOQUE_FORM_SCREEN");
    }


    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "2.0");

        SwingUtilities.invokeLater(() -> {
            new GerenciadorProjetoApp().setVisible(true);
        });
    }
}
