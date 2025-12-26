package EstacionamentoApresentacao.java;

import EstacionamentoControle.java.EstacionamentoControle;
import EstacionamentoNegocio.java.Movimentacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TelaInicialRelatorio extends JFrame implements ActionListener {
	private JComboBox cboAno;
	private JComboBox cboMes;

	public TelaInicialRelatorio() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(600,140));
		setResizable(false);
		setTitle("Filtro do Relatório");
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 15, 40));

		JLabel lblAno = new JLabel("Ano:");
		lblAno.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(lblAno);

		cboAno = new JComboBox();
		cboAno.setModel(new DefaultComboBoxModel(new String[] {"2025", "2024", "2023", "2022", "2021"}));
		cboAno.setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().add(cboAno);

		JLabel lblMes = new JLabel(" Mês:");
		lblMes.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(lblMes);

		cboMes = new JComboBox();
		cboMes.setModel(new DefaultComboBoxModel(new String[] {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"}));
		cboMes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().add(cboMes);

		JButton btnGerar = new JButton("Gerar");
		btnGerar.addActionListener(this);
		btnGerar.setFont(new Font("Tahoma", Font.BOLD, 14));
		getContentPane().add(btnGerar);

		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		int ano = Integer.parseInt((String) cboAno.getSelectedItem());
		int mes = cboMes.getSelectedIndex() + 1;

		EstacionamentoControle controle = new EstacionamentoControle();

		// Início e fim do mês para pegar todas as movimentações
		LocalDateTime dataInicio = LocalDateTime.of(ano, mes, 1, 0, 0);
		LocalDateTime dataFim = dataInicio.plusMonths(1).minusSeconds(1);

        List<Movimentacao> movimentacoes = null;
        try {
            movimentacoes = controle.emitirRelatorio(dataInicio, dataFim);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        TelaResultadoRelatorio relatorio =
				new TelaResultadoRelatorio(this, movimentacoes, dataInicio);
		relatorio.setVisible(true);
		dispose();
	}
}
