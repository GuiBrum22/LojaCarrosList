package Controller;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Model.Carros;
import Model.Clientes;

public class ClientesControl {
    private List<Clientes> clientes;
    private DefaultTableModel tableModel;
    private JTable table;

    public ClientesControl(List<Clientes> clientes, DefaultTableModel tableModel, JTable table) {
        this.clientes = clientes;
        this.tableModel = tableModel;
        this.table = table;
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        clientes = new ClientesDAO().listarTodos();
        for (Clientes cliente : clientes) {

            tableModel.addRow(
                    new Object[] { cliente.getCpf(), cliente.getNome(), cliente.getTelefone(), cliente.getCidade() });
        }
    }

    public void cadastrar(String cpf, String nome, String telefone, String cidade) {
        new ClientesDAO().cadastrar(cpf, nome, telefone, cidade);

        atualizarTabela();
    }

    public void atualizar(String cpf, String nome, String telefone, String cidade) {
        new ClientesDAO().atualizar(cpf, nome, telefone, cidade);

        atualizarTabela();
    }

    public void apagar(String cpf) {
        new ClientesDAO().apagar(cpf);
        atualizarTabela();
    }
}
