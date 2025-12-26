package EstacionamentoApresentacao.java;

import EstacionamentoControle.java.EstacionamentoControle;
import EstacionamentoControle.java.EstacionamentoException;
import EstacionamentoControle.java.VeiculoException;
import EstacionamentoNegocio.java.Movimentacao;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class TelaSaidaVeiculo extends JFrame implements ActionListener {

    private JFrame telaMenu;
    private JFormattedTextField txtPlaca;

    public TelaSaidaVeiculo(JFrame telaMenu) throws ParseException {

        this.telaMenu = telaMenu;

        setTitle("Saída de Veículo");
        setSize(400, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== Painel da placa =====
        JPanel painelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 30));

        JLabel lblPlaca = new JLabel("Placa:");
        painelCentro.add(lblPlaca);

        txtPlaca = new JFormattedTextField(new MaskFormatter("UUU-####"));
        txtPlaca.setColumns(10);
        txtPlaca.setFont(new Font("Tahoma", Font.BOLD, 16));
        painelCentro.add(txtPlaca);

        add(painelCentro, BorderLayout.CENTER);

        // ===== Painel dos botões =====
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnOk = new JButton("OK");
        btnOk.setActionCommand("ok");
        btnOk.addActionListener(this);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setActionCommand("voltar");
        btnVoltar.addActionListener(this);

        painelBotoes.add(btnOk);
        painelBotoes.add(btnVoltar);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("ok")) {

            // 1️⃣ Valida placa ANTES de qualquer coisa
            String placa = txtPlaca.getText();

            if (placa.contains("_")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Informe a placa completa",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            EstacionamentoControle controle = new EstacionamentoControle();
            Movimentacao movimentacao = null;

            try {

                movimentacao = controle.processarSaida(placa);

            } catch (VeiculoException ex ) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Falha na Saída",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }


            TelaResumoPagamento telaResumo =
                    new TelaResumoPagamento(movimentacao, telaMenu);

            telaResumo.setVisible(true);
            this.dispose();

        } else if (cmd.equals("voltar")) {
            telaMenu.setVisible(true);
            this.dispose();
        }
    }
}
