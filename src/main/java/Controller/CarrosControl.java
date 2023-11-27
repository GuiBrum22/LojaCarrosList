package Controller;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Model.Carros;

public class CarrosControl {

    private List<Carros> carros;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor que recebe a lista de carros, o modelo da tabela e a tabela
    public CarrosControl(List<Carros> carros, DefaultTableModel tableModel, JTable table) {
        this.carros = carros;
        this.tableModel = tableModel;
        this.table = table;
    }

    // Método privado para atualizar a tabela com os dados da lista de carros
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas da tabela
        carros = new CarrosDAO().listarTodos(); // Obtém a lista atualizada de carros do banco de dados
        for (Carros carro : carros) {
            // Adiciona uma nova linha à tabela com os atributos do carro
            tableModel.addRow(new Object[] { carro.getMarca(), carro.getModelo(), carro.getAno(), carro.getPlaca(), carro.getValor() });
        }
    }

    // Método para cadastrar um novo carro
    public void cadastrar(String marca, String modelo, String ano, String placa, String valor) {
        new CarrosDAO().cadastrar(marca, modelo, ano, placa, valor); // Chama o método de cadastro no DAO
        atualizarTabela(); // Atualiza a tabela após o cadastro
    }

    // Método para atualizar os dados de um carro existente
    public void atualizar(String marca, String modelo, String ano, String placa, String valor) {
        new CarrosDAO().atualizar(marca, modelo, ano, placa, valor); // Chama o método de atualização no DAO
        atualizarTabela(); // Atualiza a tabela após a atualização
    }

    // Método para apagar um carro pelo número da placa
    public void apagar(String placa) {
        new CarrosDAO().apagar(placa); // Chama o método de exclusão no DAO
        atualizarTabela(); // Atualiza a tabela após a exclusão
    }
}
