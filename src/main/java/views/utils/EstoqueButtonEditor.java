package views.utils;

import dao.EstoqueDAO;
import models.Estoque;
import views.estoque.EstoqueList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EstoqueButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private String acao;
    private EstoqueList estoqueList;

    public EstoqueButtonEditor(JCheckBox checkBox, String acao, EstoqueList estoqueList) {
        super(checkBox);
        this.acao = acao;
        this.estoqueList = estoqueList;

        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); // para finalizar a edição da célula
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
        if (isPushed) {
            int row = estoqueList.getTable().getSelectedRow();
            Estoque item = estoqueList.getTableModel().getRoupas().get(row);

            if ("editar".equalsIgnoreCase(acao)) {
                estoqueList.getAoEditar().accept(item);
            } else if ("excluir".equalsIgnoreCase(acao)) {
                int confirm = JOptionPane.showConfirmDialog(button,
                        "Deseja excluir o item?", "Confirmação",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new EstoqueDAO().remover(item.getId());
                    estoqueList.atualizarTabela();
                    JOptionPane.showMessageDialog(button, "Item removido com sucesso!");
                }
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
}
