package EstacionamentoApresentacao.java;

import EstacionamentoControle.java.EstacionamentoControle;
import EstacionamentoControle.java.EstacionamentoException;
import EstacionamentoControle.java.VeiculoException;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class TelaEntradaVeiculo extends JFrame implements ActionListener {

    private JFrame parent;
    private JTextField txtModelo;
    private JTextField txtMarca;
    private JTextField txtCor;
    private JFormattedTextField txfPlaca;
    private JButton btnOk;
    private JButton btnVoltar;

    public TelaEntradaVeiculo(JFrame parent) {

        this.parent = parent;

        setTitle("Entrada de Veículo");
        setResizable(false);
        setSize(378, 335);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        // BOTÃO VOLTAR
        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(0, 273, 362, 23);
        btnVoltar.addActionListener(e -> {
            parent.setVisible(true);
            this.dispose();
        });
        getContentPane().add(btnVoltar);

        // LABELS
        JLabel lblPlaca = new JLabel("Placa:");
        lblPlaca.setBounds(69, 56, 46, 14);
        getContentPane().add(lblPlaca);

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setBounds(69, 81, 46, 14);
        getContentPane().add(lblMarca);

        JLabel lblModelo = new JLabel("Modelo:");
        lblModelo.setBounds(69, 106, 46, 14);
        getContentPane().add(lblModelo);

        JLabel lblCor = new JLabel("Cor:");
        lblCor.setBounds(69, 131, 36, 14);
        getContentPane().add(lblCor);

        // CAMPOS TEXTO
        txtModelo = new JTextField();
        txtModelo.setBounds(137, 103, 120, 20);
        getContentPane().add(txtModelo);

        txtMarca = new JTextField();
        txtMarca.setBounds(137, 77, 120, 20);
        getContentPane().add(txtMarca);

        txtCor = new JTextField();
        txtCor.setBounds(137, 128, 120, 20);
        getContentPane().add(txtCor);

        // CAMPO PLACA (MASK CORRIGIDA)
        try {
            MaskFormatter formatter = new MaskFormatter("UUU-####");
            formatter.setPlaceholderCharacter('_');
            formatter.setAllowsInvalid(false);
            formatter.setOverwriteMode(true);

            txfPlaca = new JFormattedTextField(formatter);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro no formato da placa",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        txfPlaca.setBounds(137, 53, 120, 20);
        getContentPane().add(txfPlaca);

        // BOTÃO OK
        btnOk = new JButton("Ok");
        btnOk.setBounds(134, 223, 89, 23);
        btnOk.setActionCommand("ok");
        btnOk.addActionListener(this);
        getContentPane().add(btnOk);

        // MOSTRA A TELA
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {

        if ("ok".equals(evento.getActionCommand())) {

            EstacionamentoControle controle = new EstacionamentoControle();

            try {
                // ORDEM CORRETA
                controle.processarEntrada(
                        txfPlaca.getText().trim(),
                        txtMarca.getText().trim(),
                        txtModelo.getText().trim(),
                        txtCor.getText().trim()
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Veículo registrado com sucesso",
                        "Entrada de Veículo",
                        JOptionPane.INFORMATION_MESSAGE
                );

                parent.setVisible(true);
                this.dispose();

            } catch (EstacionamentoException e) {
                JOptionPane.showMessageDialog(
                        this,
                        e.getMessage(),
                        "Falha na Entrada",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (VeiculoException e) {
                JOptionPane.showMessageDialog(
                        this,
                        e.getMessage(),
                        "Erro no Veículo",
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
