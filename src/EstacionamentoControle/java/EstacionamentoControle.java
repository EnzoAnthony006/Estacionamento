package EstacionamentoControle.java;

import EstacionamentoNegocio.java.Movimentacao;
import EstacionamentoNegocio.java.Vagas;
import EstacionamentoNegocio.java.Veiculo;
import EstacionamentoPersistence.java.DAOEstacionamento;
import EstacionamentoUtilitario.java.EstacionamentoUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class EstacionamentoControle {

    public void processarEntrada(String placa, String marca, String modelo, String cor)
            throws EstacionamentoException, VeiculoException, SQLException {

        if (!Vagas.temVagaLivre()) {
            throw new EstacionamentoException("Estacionamento Lotado!");
        }

        if (!EstacionamentoUtil.validarPadraoPlaca(placa)) {
            throw new VeiculoException("Placa Informada Inválida");
        }

        Veiculo veiculo = new Veiculo(placa, marca, modelo, cor);
        Movimentacao movimentacao = new Movimentacao(veiculo, LocalDateTime.now());

        DAOEstacionamento dao = new DAOEstacionamento();
        dao.criar(movimentacao);

        Vagas.entrou();
    }

    public Movimentacao processarSaida(String placa) throws VeiculoException {

        if (!EstacionamentoUtil.validarPadraoPlaca(placa)) {
            throw new VeiculoException("Placa Inválida!");
        }

        DAOEstacionamento dao = new DAOEstacionamento();
        Movimentacao movimentacao = dao.buscarMovimentacaoAberta(placa);

        if (movimentacao == null) {
            throw new VeiculoException("Não existe movimentação aberta para essa placa.");
        }

        movimentacao.setDataSaida(LocalDateTime.now());
        EstacionamentoUtil.calcularValorPago(movimentacao);


        //atualizar dados
        dao.atualizar(movimentacao);

        Vagas.saiu();

        return movimentacao;
    }

    public int inicializarOcupadas() {
        return 0;
    }

    public List<Movimentacao> emitirRelatorio(LocalDateTime data, LocalDateTime dataFim) throws SQLException {
        DAOEstacionamento dao = new DAOEstacionamento();
        return dao.consultarMovimentacoes(data);


    }
}
