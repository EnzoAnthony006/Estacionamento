package EstacionamentoApresentacao.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame implements ActionListener {

	private JPasswordField txtSenha;
	private JPanel panel_2;
	private JRadioButton rdbOperacional;
	private JRadioButton rdbRelatorio;
	private JButton btnOk;

	static void main() {
		TelaLogin tela = new TelaLogin();
		tela.setVisible(true);
	}

	public TelaLogin() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(500, 150));
		setResizable(false);
		setTitle("Login");

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		btnOk = new JButton("Ok");
		btnOk.addActionListener(this);
		btnOk.setActionCommand("ok");
		panel.add(btnOk);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.SOUTH);

		JLabel lblSenha = new JLabel("Senha:");
		panel_2.add(lblSenha);

		txtSenha = new JPasswordField();
		txtSenha.setColumns(20);
		panel_2.add(txtSenha);

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);

		ButtonGroup grupo = new ButtonGroup();

		rdbOperacional = new JRadioButton("Operacional");
		rdbOperacional.setSelected(true);
		rdbOperacional.addActionListener(this);
		rdbOperacional.setActionCommand("operacional");
		grupo.add(rdbOperacional);
		panel_3.add(rdbOperacional);

		rdbRelatorio = new JRadioButton("Relatorio");
		rdbRelatorio.addActionListener(this);
		rdbRelatorio.setActionCommand("relatorio");
		grupo.add(rdbRelatorio);
		panel_3.add(rdbRelatorio);

		panel_2.setVisible(false);

		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		String cmd = evento.getActionCommand();

		if (cmd.equals("operacional")) {
			panel_2.setVisible(false);

		} else if (cmd.equals("relatorio")) {
			txtSenha.setText("");
			panel_2.setVisible(true);

		} else if (cmd.equals("ok")) {

			if (rdbOperacional.isSelected()) {
				JFrame tela = new TelaInicialMovimentacao();
				tela.setVisible(true);
				this.dispose();

			} else { // Relatório
				String senha = new String(txtSenha.getPassword());

				if (!senha.equals("Java")) {
					JOptionPane.showMessageDialog(
							this,
							"Senha Incorreta",
							"Erro",
							JOptionPane.ERROR_MESSAGE
					);
					return;
				}

				JFrame tela = new TelaInicialRelatorio();
				tela.setVisible(true);
				this.dispose();
			}
		}
	}
}
