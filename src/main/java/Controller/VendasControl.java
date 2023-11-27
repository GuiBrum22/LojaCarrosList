package Controller;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Model.Vendas;

public class VendasControl {

    private List<Vendas> vendas;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor que recebe a lista de vendas, o modelo da tabela e a tabela
    public VendasControl(List<Vendas> vendas, DefaultTableModel tableModel, JTable table) {
        this.vendas = vendas;
        this.tableModel = tableModel;
        this.table = table;
    }

    // Método privado para atualizar a tabela com os dados da lista de vendas
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas da tabela
        vendas = new VendasDAO().listarTodos(); // Obtém a lista atualizada de vendas do banco de dados
        for (Vendas venda : vendas) {
            // Adiciona uma nova linha à tabela com os atributos da venda
            tableModel.addRow(new Object[] { venda.getData(), venda.getCliente(), venda.getValor(), venda.getTipoCarro() });
        }
    }

    // Método para cadastrar uma nova venda
    public void cadastrar(String data, String cliente, String valor, String carro) {
        new VendasDAO().cadastrar(data, cliente, valor, carro); // Chama o método de cadastro no DAO
        atualizarTabela(); // Atualiza a tabela após o cadastro
    }

    // Método para atualizar os dados de uma venda existente
    public void atualizar(String data, String cliente, String valor, String carro) {
        new VendasDAO().atualizar(data, cliente, valor, carro); // Chama o método de atualização no DAO
        atualizarTabela(); // Atualiza a tabela após a atualização
    }

    // Método para apagar uma venda pelo tipo de carro
    public void apagar(String carro) {
        new VendasDAO().apagar(carro); // Chama o método de exclusão no DAO
        atualizarTabela(); // Atualiza a tabela após a exclusão
    }
}
