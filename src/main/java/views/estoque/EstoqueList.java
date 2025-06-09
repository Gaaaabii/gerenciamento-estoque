package views.estoque;

import models.Estoque;
import dao.EstoqueDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import views.utils.ButtonEditor;
import java.util.function.Consumer;

public class EstoqueList extends JPanel {
    private EstoqueListTableModel tableModel;
    public JTable table;
    private EstoqueDAO estoqueDAO = new EstoqueDAO();
    private Consumer<Estoque> aoEditar;

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

        tableModel = new EstoqueListTableModel(List.of());
        table = new JTable(tableModel);

        // Renderização e ação dos botões na tabela
        table.getColumn("Editar").setCellRenderer(new ButtonRenderer());
        table.getColumn("Excluir").setCellRenderer(new ButtonRenderer());

        table.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), "editar", this));
        table.getColumn("Excluir").setCellEditor(new ButtonEditor(new JCheckBox(), "excluir", this));

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
    }

}
