package EstacionamentoNegocio.java;

import EstacionamentoControle.java.EstacionamentoControle;

public class Vagas {

    public static final int TOTAL_VAGAS = 100;
    private static int vagasOcupadas = inicializarOcupadas();

    private Vagas() {
    }

    // Verifica se existe vaga livre
    public static boolean temVagaLivre() {
        return vagasOcupadas < TOTAL_VAGAS;
    }

    // Busca no banco quantas vagas já estão ocupadas
    public static int inicializarOcupadas() {
        try {
            EstacionamentoControle controle = new EstacionamentoControle();
            return controle.inicializarOcupadas();
        } catch (Exception e) {
            return 0;
        }
    }

    // Quantidade de vagas ocupadas
    public static int ocupadas() {
        return vagasOcupadas;
    }

    // Quantidade de vagas livres
    public static int livres() {
        return TOTAL_VAGAS - vagasOcupadas;
    }

    // Entrada de veículo
    public static void entrou() {
        vagasOcupadas++;
    }

    // Saída de veículo
    public static void saiu() {
        Vagas.vagasOcupadas--;
        }
    }

