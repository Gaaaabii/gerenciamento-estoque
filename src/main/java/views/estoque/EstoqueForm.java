package views.estoque;

import dao.EstoqueDAO;
import models.Estoque;

import javax.swing.*;
import java.awt.*;

public class EstoqueForm extends JPanel {
    private JTextField nomeField, tamanhoField, corField, quantidadeField, precoField, categoriaField;
    private JButton salvarButton;
    private EstoqueDAO estoqueDAO = new EstoqueDAO();
    private Estoque estoqueAtual;
    private Runnable aoSalvarCallback;


    public EstoqueForm() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel nomeLabel = new JLabel("Nome:");
        JLabel tamanhoLabel = new JLabel("Tamanho:");
        JLabel corLabel = new JLabel("Cor:");
        JLabel quantidadeLabel = new JLabel("Quantidade:");
        JLabel precoLabel = new JLabel("Preço Unitário:");
        JLabel categoriaLabel = new JLabel("Categoria:");

        nomeField = new JTextField(20);
        tamanhoField = new JTextField(10);
        corField = new JTextField(10);
        quantidadeField = new JTextField(5);
        precoField = new JTextField(10);
        categoriaField = new JTextField(15);
        salvarButton = new JButton("Salvar");

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y; add(nomeLabel, gbc);
        gbc.gridx = 1; add(nomeField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; add(tamanhoLabel, gbc);
        gbc.gridx = 1; add(tamanhoField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; add(corLabel, gbc);
        gbc.gridx = 1; add(corField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; add(quantidadeLabel, gbc);
        gbc.gridx = 1; add(quantidadeField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; add(precoLabel, gbc);
        gbc.gridx = 1; add(precoField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; add(categoriaLabel, gbc);
        gbc.gridx = 1; add(categoriaField, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(salvarButton, gbc);

        salvarButton.addActionListener(e -> salvarEstoque());
    }

    public void setAoSalvarCallback(Runnable callback) {
        this.aoSalvarCallback = callback;
    }

    private void salvarEstoque() {
        try {
            String nome = nomeField.getText();
            String tamanho = tamanhoField.getText();
            String cor = corField.getText();
            int quantidade = Integer.parseInt(quantidadeField.getText());
            double preco = Double.parseDouble(precoField.getText());
            String categoria = categoriaField.getText();

            if (estoqueAtual == null) {
                // Novo item
                Estoque novo = new Estoque(nome, tamanho, cor, quantidade, preco, categoria);
                estoqueDAO.salvar(novo);
            } else {
                // Edição
                estoqueAtual.setNome(nome);
                estoqueAtual.setTamanho(tamanho);
                estoqueAtual.setCor(cor);
                estoqueAtual.setQuantidade(quantidade);
                estoqueAtual.setPrecoUnitario(preco);
                estoqueAtual.setCategoria(categoria);

                estoqueDAO.atualizar(estoqueAtual);
            }

            JOptionPane.showMessageDialog(this, "Item salvo com sucesso!");

            limparCampos(); // limpa tudo inclusive estoqueAtual

            if (aoSalvarCallback != null) {
                aoSalvarCallback.run();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void preencherCampos(Estoque estoque) {
        this.estoqueAtual = estoque;
        nomeField.setText(estoque.getNome());
        tamanhoField.setText(estoque.getTamanho());
        corField.setText(estoque.getCor());
        quantidadeField.setText(String.valueOf(estoque.getQuantidade()));
        precoField.setText(String.valueOf(estoque.getPrecoUnitario()));
        categoriaField.setText(estoque.getCategoria());
    }


    public void limparCampos() {
        estoqueAtual = null;
        nomeField.setText("");
        tamanhoField.setText("");
        corField.setText("");
        quantidadeField.setText("");
        precoField.setText("");
        categoriaField.setText("");
    }

}
