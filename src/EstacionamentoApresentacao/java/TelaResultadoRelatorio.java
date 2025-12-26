package EstacionamentoApresentacao.java;

import EstacionamentoNegocio.java.Movimentacao;
import EstacionamentoUtilitario.java.EstacionamentoUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TelaResultadoRelatorio extends JFrame implements ActionListener {

	private JTable tblFaturamento;
	private JFrame parent;

	public TelaResultadoRelatorio(TelaInicialRelatorio telaInicialRelatorio,
								  List<Movimentacao> movimentacoes,
								  LocalDateTime data) {

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		parent = telaInicialRelatorio;
		setSize(new Dimension(600, 300));
		setResizable(false);
		setTitle("Relatório de Faturamento");

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(this);
		panel.add(btnOk);

		JPanel panel_1 = new JPanel(new BorderLayout());
		getContentPane().add(panel_1, BorderLayout.CENTER);

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.SOUTH);

		String textoFaturamento =
				EstacionamentoUtil.gerarTextoFaturamento(data, movimentacoes);

		JLabel lbltextoFaturamento = new JLabel(textoFaturamento);
		lbltextoFaturamento.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_2.add(lbltextoFaturamento);

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);

		Object[][] conteudoFaturamento = preencherTabela(movimentacoes);
		tblFaturamento = new JTable();
		tblFaturamento.setModel(new DefaultTableModel(
				conteudoFaturamento,
				new String[]{"Placa", "Entrada", "Saída", "Valor"}
		));

		scrollPane.setViewportView(tblFaturamento);
		setLocationRelativeTo(null);
	}

	private Object[][] preencherTabela(List<Movimentacao> movimentacoes) {

		// Conta apenas movimentações NÃO nulas
		int totalValidas = 0;
		for (Movimentacao m : movimentacoes) {
			if (m != null) {
				totalValidas++;
			}
		}

		Object[][] conteudo = new Object[totalValidas][4];
		int linha = 0;

		for (Movimentacao m : movimentacoes) {

			if (m == null) {
				continue; // ignora posição nula
			}

			conteudo[linha][0] = m.getVeiculo().getPlaca();
			conteudo[linha][1] =
					EstacionamentoUtil.getDisplayData(m.getDataEntrada());
			conteudo[linha][2] =
					EstacionamentoUtil.getDisplayData(m.getDataSaida());
			conteudo[linha][3] = movimentacoes.get(0).getValor();

			linha++;
		}

		return conteudo;
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		parent.setVisible(true);
		dispose();
	}
}
