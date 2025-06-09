package views.estoque;

import models.Estoque;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class EstoqueListTableModel extends AbstractTableModel {
    private List<Estoque> roupas;

    private final String[] colunas = {
            "ID", "Nome", "Tamanho", "Cor", "Quantidade", "Preço Unitário", "Categoria", "Editar", "Excluir"
    };

    public EstoqueListTableModel(List<Estoque> roupas) {
        this.roupas = roupas;
    }

    public void setRoupas(List<Estoque> roupas) {
        this.roupas = roupas;
        fireTableDataChanged();
    }

    public List<Estoque> getRoupas() {
        return roupas;
    }

    @Override
    public int getRowCount() {
        return roupas != null ? roupas.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Estoque roupa = roupas.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> roupa.getId();
            case 1 -> roupa.getNome();
            case 2 -> roupa.getTamanho();
            case 3 -> roupa.getCor();
            case 4 -> roupa.getQuantidade();
            case 5 -> roupa.getPrecoUnitario();
            case 6 -> roupa.getCategoria();
            case 7 -> "Editar";
            case 8 -> "Excluir";
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 7 || columnIndex == 8; // apenas Editar/Excluir são clicáveis
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
}
