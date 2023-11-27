package Controller;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Model.Clientes;

public class ClientesControl {
    
    private List<Clientes> clientes;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor que recebe a lista de clientes, o modelo da tabela e a tabela
    public ClientesControl(List<Clientes> clientes, DefaultTableModel tableModel, JTable table) {
        this.clientes = clientes;
        this.tableModel = tableModel;
        this.table = table;
    }

    // Método privado para atualizar a tabela com os dados da lista de clientes
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas da tabela
        clientes = new ClientesDAO().listarTodos(); // Obtém a lista atualizada de clientes do banco de dados
        for (Clientes cliente : clientes) {
            // Adiciona uma nova linha à tabela com os atributos do cliente
            tableModel.addRow(new Object[] { cliente.getCpf(), cliente.getNome(), cliente.getTelefone(), cliente.getCidade() });
        }
    }

    // Método para cadastrar um novo cliente
    public void cadastrar(String cpf, String nome, String telefone, String cidade) {
        new ClientesDAO().cadastrar(cpf, nome, telefone, cidade); // Chama o método de cadastro no DAO
        atualizarTabela(); // Atualiza a tabela após o cadastro
    }

    // Método para atualizar os dados de um cliente existente
    public void atualizar(String cpf, String nome, String telefone, String cidade) {
        new ClientesDAO().atualizar(cpf, nome, telefone, cidade); // Chama o método de atualização no DAO
        atualizarTabela(); // Atualiza a tabela após a atualização
    }

    // Método para apagar um cliente pelo número do CPF
    public void apagar(String cpf) {
        new ClientesDAO().apagar(cpf); // Chama o método de exclusão no DAO
        atualizarTabela(); // Atualiza a tabela após a exclusão
    }
}
