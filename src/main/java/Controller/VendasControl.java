package Controller;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Model.Carros;
import Model.Vendas;

public class VendasControl {

    private List<Vendas> vendas;
    private DefaultTableModel tableModel;
    private JTable table;

    public VendasControl(List<Vendas> vendas, DefaultTableModel tableModel, JTable table) {
        this.vendas = vendas;
        this.tableModel = tableModel;
        this.table = table;
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodos();
        for (Vendas venda : vendas) {

            tableModel.addRow(
                    new Object[] { venda.getData(), venda.getCliente(), venda.getValor(), venda.getTipoCarro() });
        }
    }

    public void cadastrar(String data, String cliente, String valor, String carro) {
        new VendasDAO().cadastrar(data, cliente, valor, carro);

        atualizarTabela();
    }

    public void atualizar(String data, String cliente, String valor, String carro) {
        new VendasDAO().atualizar(data, cliente, valor, carro);

        atualizarTabela();
    }

    public void apagar(String carro) {
        new VendasDAO().apagar(carro);

        atualizarTabela();
    }

}
