package views.estoque;

import models.Estoque;
import dao.EstoqueDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EstoqueList extends JPanel {
    private EstoqueListTableModel tableModel;
    private JTable table;
    private EstoqueDAO estoqueDAO = new EstoqueDAO();

    public EstoqueList() {
        this.initComponents();
        this.loadEstoque();
    }

    private void loadEstoque() {
        List<Estoque> roupas = estoqueDAO.listarTodos(); // ajuste aqui
        tableModel.setRoupas(roupas);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        tableModel = new EstoqueListTableModel(List.of());
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
