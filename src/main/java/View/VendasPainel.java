package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
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

import Controller.CarrosControl;
import Controller.CarrosDAO;
import Controller.ClientesControl;
import Controller.ClientesDAO;
import Controller.VendasControl;
import Controller.VendasDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.awt.*;

import Model.Carros;
import Model.Vendas;
import Model.Clientes;

public class VendasPainel extends JPanel {

    private JTextField inputData;
    private JTextField inputCliente;
    private JTextField inputValor;
    private JTextField inputCarro;

    private JLabel labelData;
    private JLabel labelCliente;
    private JLabel labelValor;
    private JLabel labelCarro;

    private DefaultTableModel tableModel;
    private JTable table;
    private List<Vendas> vendas = new ArrayList<>();
    private List<Carros> carros;
    private List<Clientes> clientes;
    private int linhaSelecionada = -1;
    private JButton cadastrarButton, apagarButton, editarButton, atualizarButton;

    JComboBox<String> carrosComboBox;
    JComboBox<String> clientesComboBox;

    public VendasPainel() {
        JPanel painel1 = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel();
        JPanel buttons = new JPanel();

        carrosComboBox = new JComboBox<>();
        clientesComboBox = new JComboBox<>();

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Cliente");
        tableModel.addColumn("Data");
        tableModel.addColumn("Carro");
        tableModel.addColumn("Valor");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);

        inputCliente = new JTextField(20);
        inputData = new JTextField(10);
        inputCarro = new JTextField(20);
        inputValor = new JTextField(10);

        labelData = new JLabel("Data");
        labelCarro = new JLabel("Carro");
        labelValor = new JLabel("Valor");

        cadastrarButton = new JButton("Comprar");
        apagarButton = new JButton("Apagar");
        editarButton = new JButton("Editar");
        atualizarButton = new JButton("Atualizar");

        inputPanel.add(labelData);
        inputPanel.add(inputData);

        inputPanel.add(labelValor);
        inputPanel.add(inputValor);

        buttons.add(cadastrarButton);
        buttons.add(editarButton);
        buttons.add(apagarButton);
        buttons.add(atualizarButton);

        this.add(painel1);
        painel1.add(scrollPane, BorderLayout.CENTER);
        painel1.add(inputPanel, BorderLayout.NORTH);
        painel1.add(buttons, BorderLayout.SOUTH);

        carrosComboBox.addItem("Selecione um Carro");
        carros = new CarrosDAO().listarTodos();
        for (Carros carro : carros) {
            carrosComboBox.addItem(carro.getMarca() + " " + carro.getModelo());
        }

        clientesComboBox.addItem("Selecione um cliente");
        clientes = new ClientesDAO().listarTodos();

        for (Clientes cliente : clientes) {
            clientesComboBox.addItem(cliente.getNome() + " " + cliente.getCpf());
        }

        inputPanel.add(clientesComboBox);
        inputPanel.add(carrosComboBox);

        new VendasDAO().criaTabela();

        atualizarTabela();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    inputData.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputCliente.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputValor.setText((String) table.getValueAt(linhaSelecionada, 2));
                    inputCarro.setText((String) table.getValueAt(linhaSelecionada, 3));
                }
            }
        });

        VendasControl operacoes = new VendasControl(vendas, tableModel, table);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = inputData.getText();
                String valor = inputValor.getText();
                String clienteSelecionado = (String) clientesComboBox.getSelectedItem();
                String carroSelecionado = (String) carrosComboBox.getSelectedItem();

                if (data.isEmpty() || valor.isEmpty() || clienteSelecionado.equals("Selecione um cliente")
                        || carroSelecionado.equals("Selecione um Carro")) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                } else {
                    if (!valor.matches("[0-9]+")) {
                        JOptionPane.showMessageDialog(null, "O campo 'Valor' deve conter apenas números.");
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        dateFormat.setLenient(false);

                        try {

                            Date parsedDate = dateFormat.parse(data);
                            if (!data.equals(dateFormat.format(parsedDate))) {
                                throw new ParseException("Formato inválido", 0);
                            }

                            String cliente = clienteSelecionado.split(" ")[0];
                            operacoes.cadastrar(data, cliente, valor, carroSelecionado);
                            inputData.setText("");
                            inputValor.setText("");
                            clientesComboBox.setSelectedIndex(0);
                            carrosComboBox.setSelectedIndex(0);
                            new CarrosDAO().apagar(carroSelecionado);
                            JOptionPane.showMessageDialog(null, "Venda cadastrada!");
                        } catch (ParseException ex) {
                            JOptionPane.showMessageDialog(null,
                                    "Formato de data inválido. Utilize o formato dd/MM/yyyy.");
                        }
                    }
                }
            }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clienteSelecionado = (String) clientesComboBox.getSelectedItem();
                String carroSelecionado = (String) carrosComboBox.getSelectedItem();
                if (inputCarro.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecione algo para editar");
                } else {
                    operacoes.atualizar(inputData.getText(), clienteSelecionado, inputValor.getText(),
                            carroSelecionado);

                    inputData.setText("");
                    inputValor.setText("");
                    clientesComboBox.setSelectedIndex(0);
                    carrosComboBox.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Informação editada com Sucesso!");
                }

            }
        });

        apagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carroSelecionado = (String) carrosComboBox.getSelectedItem();
                if (inputCarro.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para excluir.");
                } else {
                    int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja excluir os campos?",
                            "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {

                        operacoes.apagar(inputCarro.getText());
                        JOptionPane.showMessageDialog(null, "A venda foi excluida!");

                        inputData.setText("");
                        inputValor.setText("");
                        clientesComboBox.setSelectedIndex(0);
                        carrosComboBox.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(null, "A venda não foi excluida");
                    }
                }
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarComboBoxClientes();
                atualizarComboBoxCarros();
            }
        });

    }

    private void atualizarTabela() {

        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodos();
        for (Vendas venda : vendas) {
            tableModel.addRow(
                    new Object[] { venda.getCliente(), venda.getData(), venda.getTipoCarro(), venda.getValor() });
        }

    }

    private void atualizarComboBoxClientes() {
        clientesComboBox.removeAllItems();
        clientesComboBox.addItem("Selecione um cliente");
        clientes = new ClientesDAO().listarTodos();
        for (Clientes cliente : clientes) {
            clientesComboBox.addItem(cliente.getNome() + " " + cliente.getCpf());
        }
    }

    private void atualizarComboBoxCarros() {
        carrosComboBox.removeAllItems();
        carrosComboBox.addItem("Selecione um Carro");
        carros = new CarrosDAO().listarTodos();
        for (Carros carro : carros) {
            carrosComboBox.addItem(carro.getMarca() + " " + carro.getModelo());
        }
    }
}
