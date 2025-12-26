package EstacionamentoApresentacao.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class TelaInicialMovimentacao extends JFrame implements ActionListener {

	private JButton btnEntrar;
	private JButton btnSair;

	static void main() {
		TelaInicialMovimentacao tela = new TelaInicialMovimentacao();
		tela.setVisible(true);
	}

	public TelaInicialMovimentacao() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(450, 300));
		setResizable(false);
		setTitle("Sistema de Estacionamento");
		getContentPane().setLayout(new GridLayout(1, 2, 0, 0));

		btnEntrar = new JButton("");
		btnEntrar.setIcon(new ImageIcon(
				TelaInicialMovimentacao.class.getResource("/recursos/Captura de tela 2025-12-17 132231.png")
		));
		btnEntrar.addActionListener(this);
		btnEntrar.setActionCommand("entrada");
		getContentPane().add(btnEntrar);

		btnSair = new JButton("");
		btnSair.setIcon(new ImageIcon(
				TelaInicialMovimentacao.class.getResource("/recursos/Captura de tela 2025-12-17 132250.png")
		));
		btnSair.addActionListener(this);
		btnSair.setActionCommand("saida");
		getContentPane().add(btnSair);

		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		JFrame tela;

		if (cmd.equals("entrada")) {
            tela = new TelaEntradaVeiculo(this);
        } else {
            try {
                tela = new TelaSaidaVeiculo(this);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }

		tela.setVisible(true);
		this.setVisible(false);
	}
}
