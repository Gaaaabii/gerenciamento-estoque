package views.utils;

import models.Usuario;
import models.dao.UsuarioDAO;
import views.user.UserList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private String acao;
    private UserList userList;

    public ButtonEditor(JCheckBox checkBox, String acao, UserList userList) {
        super(checkBox);
        this.acao = acao;
        this.userList = userList;

        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); // Finaliza a edição antes de executar ação
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
            int row = userList.getTable().getSelectedRow();
            Usuario usuario = userList.getTableModel().getUsuarioAt(row);

            if ("editar".equalsIgnoreCase(acao)) {
                userList.getOnEditar().accept(usuario);
            } else if ("excluir".equalsIgnoreCase(acao)) {
                int confirm = JOptionPane.showConfirmDialog(button,
                        "Deseja excluir o usuário?", "Confirmação",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new UsuarioDAO().delete(usuario.getId());
                    userList.atualizarTabela();
                    JOptionPane.showMessageDialog(button, "Usuário removido com sucesso!");
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
