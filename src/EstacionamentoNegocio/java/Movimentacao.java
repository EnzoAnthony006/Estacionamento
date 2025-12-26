package EstacionamentoNegocio.java;

import java.time.LocalDateTime;

public class Movimentacao {

    private Veiculo veiculo;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    // 🔥 CONSTRUTOR CORRETO 🔥
    public Movimentacao(Veiculo veiculo, LocalDateTime dataEntrada) {
        this.veiculo = veiculo;
        this.dataEntrada = dataEntrada;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }

    public void setValor(double valor) {
    }

    public double getValor() {
        return 0;
    }



}
