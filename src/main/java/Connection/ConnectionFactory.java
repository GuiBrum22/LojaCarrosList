package persistencia; // Considere um nome de pacote mais específico, como "persistencia" ou "acessobd."

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {

    // Detalhes da conexão com o banco de dados
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String usuario = "postgres";
    private static final String senha = "postgres";

    // Obter uma conexão com o banco de dados
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            // Tratamento de exceção: Lança uma exceção de tempo de execução com uma mensagem significativa.
            throw new RuntimeException("Erro ao obter conexão com o banco de dados.", e);
        }
    }

    // Fechar a conexão com o banco de dados
    public static void closeConnection(Connection connection) {
        try {
            // Usando try-with-resources para gerenciamento automático de recursos
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            // Tratamento de exceção: Registre o erro ou manipule de acordo com os requisitos da sua aplicação.
            ex.printStackTrace();
        }
    }

    // Fechar a conexão com o banco de dados e o PreparedStatement
    public static void closeConnection(Connection connection, PreparedStatement stmt) {
        closeConnection(connection);
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Fechar a conexão com o banco de dados, o PreparedStatement e o ResultSet
    public static void closeConnection(Connection connection, PreparedStatement stmt, ResultSet rs) {
        closeConnection(connection, stmt);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
