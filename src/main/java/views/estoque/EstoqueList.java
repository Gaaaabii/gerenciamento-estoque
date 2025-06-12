package views.estoque;

import models.Estoque;
import dao.EstoqueDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import views.utils.EstoqueButtonEditor;
import javax.swing.table.TableColumn;
import views.estoque.ButtonRenderer;

import java.util.function.Consumer;

public class EstoqueList extends JPanel {
    private EstoqueListTableModel tableModel;
    public JTable table;
    private EstoqueDAO estoqueDAO = new EstoqueDAO();
    private Consumer<Estoque> aoEditar;
    private JTextField campoPesquisa;
    private JButton botaoPesquisar;

    public EstoqueList() {
        this.initComponents();
        this.loadEstoque();
    }

    private void loadEstoque() {
        List<Estoque> roupas = estoqueDAO.listarTodos();
        tableModel.setRoupas(roupas);
    }

    private void removerSelecionado() {
        int linhaSelecionada = table.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para remover.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente remover este item?",
                "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(linhaSelecionada, 0); // Coluna 0 = ID
            estoqueDAO.remover(id);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Item removido com sucesso!");
        }
    }


    public void atualizarTabela() {
        loadEstoque();
    }

    public JTable getTable() {
        return table;
    }

    public EstoqueList(Consumer<Estoque> aoEditar) {
        this.aoEditar = aoEditar;
        this.initComponents();
        this.loadEstoque();
    }

    public EstoqueListTableModel getTableModel() {
        return tableModel;
    }

    public Consumer<Estoque> getAoEditar() {
        return aoEditar;
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Campo de pesquisa
        campoPesquisa = new JTextField(20);
        botaoPesquisar = new JButton("Pesquisar");

        botaoPesquisar.addActionListener(e -> {
            String nomeDigitado = campoPesquisa.getText().trim();
            if (nomeDigitado.isEmpty()) {
                atualizarTabela(); // mostra tudo
            } else {
                filtrarPorNome(nomeDigitado); // filtra
            }
        });

        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelPesquisa.add(new JLabel("Pesquisar por Nome:"));
        painelPesquisa.add(campoPesquisa);
        painelPesquisa.add(botaoPesquisar);
        add(painelPesquisa, BorderLayout.NORTH);

        tableModel = new EstoqueListTableModel(List.of());
        table = new JTable(tableModel);

        TableColumn editarCol = table.getColumnModel().getColumn(7);
        editarCol.setCellRenderer(new ButtonRenderer());
        editarCol.setCellEditor(new EstoqueButtonEditor(new JCheckBox(), "editar", this));

        TableColumn excluirCol = table.getColumnModel().getColumn(8);
        excluirCol.setCellRenderer(new ButtonRenderer());
        excluirCol.setCellEditor(new EstoqueButtonEditor(new JCheckBox(), "excluir", this));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void filtrarPorNome(String nome) {
        List<Estoque> todos = estoqueDAO.listarTodos();
        List<Estoque> filtrados = todos.stream()
                .filter(e -> e.getNome().toLowerCase().contains(nome.toLowerCase()))
                .toList();

        tableModel.setRoupas(filtrados);
    }
}