package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.Clientes;
import Connection.ConnectionFactory;

public class ClientesDAO {

    private Connection connection;
    private List<Clientes> clientes;

    // Construtor que obtém uma conexão ao criar uma instância
    public ClientesDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    // Método para criar a tabela no banco de dados se ela não existir
    public void criaTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS clientes_lojacarros (NOME VARCHAR(255), TELEFONE VARCHAR(255), CIDADE VARCHAR(255), CPF VARCHAR(255) PRIMARY KEY)";
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela criada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    // Método para listar todos os clientes no banco de dados
    public List<Clientes> listarTodos() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        clientes = new ArrayList<>();

        try {
            stmt = connection.prepareStatement("SELECT * FROM clientes_lojacarros");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Clientes cliente = new Clientes(
                        rs.getString("CPF"),
                        rs.getString("Nome"),
                        rs.getString("Telefone"),
                        rs.getString("Cidade"));
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
        return clientes;
    }

    // Método para cadastrar um novo cliente no banco de dados
    public void cadastrar(String cpf, String nome, String telefone, String cidade) {
        PreparedStatement stmt = null;

        String sql = "INSERT INTO clientes_lojacarros (cpf, nome, telefone, cidade) VALUES (?, ?, ?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.setString(2, nome);
            stmt.setString(3, telefone);
            stmt.setString(4, cidade);
            stmt.executeUpdate();
            System.out.println("Dados inseridos");
            JOptionPane.showMessageDialog(null, "Cliente cadastrado");
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                JOptionPane.showMessageDialog(null, "Erro: O CPF inserido já existe.");
            } else {
                throw new RuntimeException("Erro ao inserir dados.", e);
            }
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Método para atualizar os dados de um cliente no banco de dados
    public void atualizar(String nome, String telefone, String cidade, String cpf) {
        PreparedStatement stmt = null;

        String sql = "UPDATE clientes_lojacarros SET nome = ?, telefone = ?, cidade = ? WHERE cpf = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, telefone);
            stmt.setString(3, cidade);
            stmt.setString(4, cpf);
            stmt.executeUpdate();

            System.out.println("Dados atualizados");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Método para apagar um cliente do banco de dados pelo número do CPF
    public void apagar(String cpf) {
        PreparedStatement stmt = null;

        String sql = "DELETE FROM clientes_lojacarros WHERE cpf = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.executeUpdate();
            System.out.println("Dado apagado.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apagar dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
}
