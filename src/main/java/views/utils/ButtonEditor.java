package views.utils;

import dao.EstoqueDAO;
import models.Estoque;
import views.estoque.EstoqueList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private String acao;
    private EstoqueList estoqueList;

    public ButtonEditor(JCheckBox checkBox, String acao, EstoqueList estoqueList) {
        super(checkBox);
        this.acao = acao;
        this.estoqueList = estoqueList;

        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        System.out.println("Botão clicado: " + acao);
        if (isPushed) {
            int row = estoqueList.getTable().getSelectedRow();
            int id = (int) estoqueList.getTable().getModel().getValueAt(row, 0); // Coluna 0 = ID

            if ("excluir".equalsIgnoreCase(acao)) {
                int confirm = JOptionPane.showConfirmDialog(button,
                        "Deseja remover o item com ID " + id + "?", "Confirmação",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new EstoqueDAO().remover(id);
                    estoqueList.atualizarTabela();
                    JOptionPane.showMessageDialog(button, "Item removido.");
                }
            } else if ("editar".equalsIgnoreCase(acao)) {
                Estoque item = estoqueList.getTableModel().getRoupas().get(row);
                estoqueList.getAoEditar().accept(item);
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
