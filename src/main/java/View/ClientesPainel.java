package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import Controller.CarrosDAO;
import Controller.ClientesControl;
import Controller.ClientesDAO;

import Model.Carros;
import Model.Clientes;

public class ClientesPainel extends JPanel {

    private JTextField inputCpf;
    private JTextField inputNome;
    private JTextField inputTelefone;
    private JTextField inputCidade;

    private JLabel labelCpf;
    private JLabel labelNome;
    private JLabel labelTelefone;
    private JLabel labelCidade;

    private DefaultTableModel tableModel;
    private JTable table;
    private List<Clientes> clientes = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JButton cadastrarButton, apagarButton, editarButton;

    public ClientesPainel() {
        JPanel painel1 = new JPanel();
        JPanel inputPanel = new JPanel();
        JPanel buttons = new JPanel();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        painel1.setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Cpf");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Telefone");
        tableModel.addColumn("Cidade");
        table = new JTable(tableModel);
        table.setBackground(Color.LIGHT_GRAY);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);

        inputCpf = new JTextField(20);
        inputCpf.setFont(new Font("Arial", Font.PLAIN, 16));

        inputNome = new JTextField(20);
        inputNome.setFont(new Font("Arial", Font.PLAIN, 16));

        inputTelefone = new JTextField(20);
        inputTelefone.setFont(new Font("Arial", Font.PLAIN, 16));

        inputCidade = new JTextField(20);
        inputCidade.setFont(new Font("Arial", Font.PLAIN, 16));

        labelCpf = new JLabel("CPF");
        labelCpf.setFont(new Font("Arial", Font.PLAIN, 16));

        labelNome = new JLabel("Nome");
        labelNome.setFont(new Font("Arial", Font.PLAIN, 16));

        labelTelefone = new JLabel("Telefone");
        labelTelefone.setFont(new Font("Arial", Font.PLAIN, 16));

        labelCidade = new JLabel("Cidade");
        labelCidade.setFont(new Font("Arial", Font.PLAIN, 16));

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setFont(new Font("Arial", Font.PLAIN, 16));
        cadastrarButton.setBackground(Color.GREEN);

        apagarButton = new JButton("Apagar");
        apagarButton.setFont(new Font("Arial", Font.PLAIN, 16));

        editarButton = new JButton("Editar");
        editarButton.setFont(new Font("Arial", Font.PLAIN, 16));

        inputPanel.add(labelCpf);
        inputPanel.add(inputCpf);
        inputPanel.add(labelNome);
        inputPanel.add(inputNome);
        inputPanel.add(labelTelefone);
        inputPanel.add(inputTelefone);
        inputPanel.add(labelCidade);
        inputPanel.add(inputCidade);

        buttons.add(cadastrarButton);
        buttons.add(editarButton);
        buttons.add(apagarButton);

        this.add(painel1);
        painel1.add(scrollPane, BorderLayout.CENTER);
        painel1.add(inputPanel, BorderLayout.NORTH);
        painel1.add(buttons, BorderLayout.SOUTH);

        new ClientesDAO().criaTabela();

        atualizarTabela();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    inputCpf.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputNome.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputTelefone.setText((String) table.getValueAt(linhaSelecionada, 2));
                    inputCidade.setText((String) table.getValueAt(linhaSelecionada, 3));
                }
            }
        });

        ClientesControl operacoes = new ClientesControl(clientes, tableModel, table);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputCpf.getText().isEmpty() || inputNome.getText().isEmpty() || inputTelefone.getText().isEmpty()
                        || inputCidade.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "ATENÇÃO! \nExistem campos em branco");
                } else {
                    if (!validarFormatoCPF(inputCpf.getText())) {
                        JOptionPane.showMessageDialog(null,
                                "CPF inválido! O CPF deve conter apenas números e ter 11 dígitos.");
                    } else {

                        operacoes.cadastrar(inputCpf.getText(), inputNome.getText(), inputTelefone.getText(),
                                inputCidade.getText());

                        inputCpf.setText("");
                        inputNome.setText("");
                        inputTelefone.setText("");
                        inputCidade.setText("");
                    }
                }

            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputCpf.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecione algo para editar");
                } else {
                    operacoes.atualizar(labelCpf.getText(), labelNome.getText(),
                            labelTelefone.getText(), labelCidade.getText());

                    // Limpa os campos de entrada após a operação de atualização
                    inputCpf.setText("");
                    inputNome.setText("");
                    inputTelefone.setText("");
                    inputCidade.setText("");
                    JOptionPane.showMessageDialog(null, "Informação editada com Sucesso!");
                }

            }
        });

        apagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputCpf.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecione um cliente para apagar.");
                } else {
                    int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja apagar os campos?",
                            "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {
                        // Chama o método "apagar" do objeto operacoes com o valor do campo de entrada
                        // "placa"
                        operacoes.apagar(inputCpf.getText());
                        JOptionPane.showMessageDialog(null, "O Cliente " + inputNome.getText() + " de CPF "
                                + inputCpf.getText() + " foi deletado!");
                        // Limpa os campos de entrada após a operação de exclusão
                        inputCpf.setText("");
                        inputNome.setText("");
                        inputTelefone.setText("");
                        inputCidade.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "O cliente não foi deletado!");
                    }
                }
            }
        });
    }

    // atualizar Tabela de Carros com o Banco de Dados
    private void atualizarTabela() {
        // atualizar tabela pelo banco de dados
        tableModel.setRowCount(0);
        clientes = new ClientesDAO().listarTodos();
        for (Clientes cliente : clientes) {
            tableModel.addRow(
                    new Object[] { cliente.getCpf(), cliente.getNome(), cliente.getTelefone(), cliente.getCidade() });
        }
    }

    private boolean validarFormatoCPF(String cpf) {
        // Remove caracteres não numéricos do CPF
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF possui 11 dígitos (considerando os caracteres separadores)
        return cpf.length() == 11;
    }
}
