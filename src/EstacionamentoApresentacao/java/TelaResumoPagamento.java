package EstacionamentoApresentacao.java;

import EstacionamentoNegocio.java.Movimentacao;
import EstacionamentoUtilitario.java.EstacionamentoUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaResumoPagamento extends JFrame implements ActionListener {
	private JFrame parent;
    public TelaResumoPagamento(Movimentacao movimentacao, JFrame parent) {
		this.parent = parent;
		setSize(new Dimension(430,300));
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setResizable(false);
    	setTitle("Resumo de Pagamento");
    	getContentPane().setLayout(null);
    	
    	JLabel lblPlaca = new JLabel("Placa:");
    	lblPlaca.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	lblPlaca.setBounds(94, 45, 46, 14);
    	getContentPane().add(lblPlaca);
    	
    	JLabel lblDataEntrada = new JLabel("Entrada:");
    	lblDataEntrada.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	lblDataEntrada.setBounds(82, 77, 80, 14);
    	getContentPane().add(lblDataEntrada);
    	
    	JLabel lblDataSaida = new JLabel("Saida:");
    	lblDataSaida.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	lblDataSaida.setBounds(94, 116, 46, 14);
    	getContentPane().add(lblDataSaida);
    	
    	JLabel lblValor = new JLabel("Valor:");
    	lblValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
    	lblValor.setBounds(94, 151, 46, 14);
    	getContentPane().add(lblValor);

		String sPlaca = movimentacao.getVeiculo().getPlaca();
    	JLabel lblValPlaca = new JLabel(sPlaca);
    	lblValPlaca.setFont(new Font("Tahoma", Font.BOLD, 14));
    	lblValPlaca.setBounds(211, 45, 178, 14);
    	getContentPane().add(lblValPlaca);


		String sEntrada = EstacionamentoUtil.getDisplayData(movimentacao.getDataEntrada());
    	JLabel lblValDataEntrada = new JLabel(sEntrada);
    	lblValDataEntrada.setFont(new Font("Tahoma", Font.BOLD, 14));
    	lblValDataEntrada.setBounds(211, 77, 154, 14);
    	getContentPane().add(lblValDataEntrada);

		String sSaida = EstacionamentoUtil.getDisplayData(movimentacao.getDataSaida());
    	JLabel lblValDataSaida = new JLabel(sSaida);
    	lblValDataSaida.setFont(new Font("Tahoma", Font.BOLD, 14));
    	lblValDataSaida.setBounds(211, 116, 203, 14);
    	getContentPane().add(lblValDataSaida);

		String sValor = "R$ " + movimentacao.getValor();
    	JLabel lblValValor = new JLabel(sValor);
    	lblValValor.setFont(new Font("Tahoma", Font.BOLD, 14));
    	lblValValor.setBounds(211, 151, 116, 14);
    	getContentPane().add(lblValValor);
    	
    	JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(this);
    	btnOk.setBounds(137, 212, 89, 23);
    	getContentPane().add(btnOk);

		setLocationRelativeTo(null);

    }

	@Override
	public void actionPerformed(ActionEvent e) {
		parent.setVisible(true);
		dispose();

	}
}
