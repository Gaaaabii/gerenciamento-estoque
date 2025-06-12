package views.user;

import models.Usuario;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserListTableModel extends AbstractTableModel {
    private List<Usuario> users;
    private final String[] columns = {"ID", "Nome", "Email", "Senha", "Editar", "Excluir"};

    public UserListTableModel(List<Usuario> users) {
        this.users = users;
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }

    @Override
    public int getRowCount() {
        return this.users.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario user = this.users.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> user.getId();
            case 1 -> user.getNome();
            case 2 -> user.getEmail();
            case 3 -> user.getSenha();
            case 4 -> "Editar";
            case 5 -> "Excluir";
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4 || columnIndex == 5; // Somente "Editar" e "Excluir"
    }


    public void setUsers(List<Usuario> users) {
        this.users = users;
        fireTableDataChanged();
    }

    public Usuario getUserAt(int rowIndex) {
        return users.get(rowIndex);
    }
    public Usuario getUsuarioAt(int rowIndex) {
        return users.get(rowIndex);
    }

}
