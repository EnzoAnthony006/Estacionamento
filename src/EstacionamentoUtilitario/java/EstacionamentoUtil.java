package EstacionamentoUtilitario.java;

import EstacionamentoNegocio.java.Movimentacao;
import EstacionamentoNegocio.java.Tarifario;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Pattern;

public class EstacionamentoUtil {

    private static Properties props;

    static {
        try {
            props = new Properties();
            InputStream input = EstacionamentoUtil.class
                    .getClassLoader()
                    .getResourceAsStream("Recursos/configuration.txt");

            if (input == null) {
                throw new RuntimeException("Arquivo configuration.txt não encontrado em Recursos");
            }

            props.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar configuration.txt", e);
        }
    }

    public static String get(String chave) {
        return props.getProperty(chave);
    }

    public static boolean validarPadraoPlaca(String placa) {
        if (placa == null) return false;
        String padrao = "^[A-Z]{3}-\\d{4}$";
        return Pattern.matches(padrao, placa);
    }

    public static String getDataAsString(LocalDateTime dataHoraEntrada) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataHoraEntrada.format(formatter);
    }

    public static void calcularValorPago(Movimentacao movimentacao) {
        LocalDateTime inicio = movimentacao.getDataEntrada();
        LocalDateTime fim = movimentacao.getDataSaida();

        long minutosTotais = ChronoUnit.MINUTES.between(inicio, fim);

        double valor = Tarifario.VALOR_HORA;

        if (minutosTotais > 60) {
            long minutosExcedentes = minutosTotais - 60;
            valor += (minutosExcedentes / Tarifario.INCREMENTO_MINUTOS)
                    * Tarifario.VALOR_INCREMENTAL;
        }

        movimentacao.setValor(valor);
    }

    public static LocalDateTime getDate(String dataEntrada) {
        return LocalDateTime.parse(
                dataEntrada,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")
        );
    }

    public static String getDisplayData(LocalDateTime data) {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public static String gerarTextoFaturamento(LocalDateTime data, List<Movimentacao> movimentacoes) {
        double totalFaturado = 0;
        String texto = "";

        for (Movimentacao movimentacao : movimentacoes) {
            if (movimentacao != null) {
                totalFaturado += movimentacao.getValor();
            }
        }

        String sAno = "" + data.getYear();
        String sMes = "" + data.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());

        texto = "Faturamento do mês de  " + sMes;
        texto += " de  " + sAno + "Foi de R$ " + totalFaturado;

        return texto;
    }
}
