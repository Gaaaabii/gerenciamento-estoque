package views.user;

import models.Usuario;
import models.dao.UsuarioDAO;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import views.utils.ButtonEditor;
import views.estoque.ButtonRenderer;
import javax.swing.JCheckBox;


public class UserList extends JPanel {
    private UserListTableModel tableModel;
    private JTable table;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Consumer<Usuario> onEditar;


        public UserList(Consumer<Usuario> onEditar) {
            this.onEditar = onEditar;
            initComponents();
            loadUsers();
        }

        private void initComponents() {
            setLayout(new BorderLayout());

            tableModel = new UserListTableModel(List.of());
            table = new JTable(tableModel);

            TableColumn editarCol = table.getColumnModel().getColumn(4);
            editarCol.setCellRenderer(new ButtonRenderer());
            editarCol.setCellEditor(new ButtonEditor(new JCheckBox(), "editar", this));

            TableColumn excluirCol = table.getColumnModel().getColumn(5);
            excluirCol.setCellRenderer(new ButtonRenderer());
            excluirCol.setCellEditor(new ButtonEditor(new JCheckBox(), "excluir", this));

            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
        }

        private void loadUsers() {
            List<Usuario> users = usuarioDAO.findAll();
            tableModel.setUsers(users);
        }

        public void atualizarTabela() {
            loadUsers();
        }

        public JTable getTable() {
            return this.table;
        }

        public UserListTableModel getTableModel() {
            return this.tableModel;
        }
    public Consumer<Usuario> getOnEditar() {
        return this.onEditar;
    }

}