package EstacionamentoPersistence.java;

import EstacionamentoNegocio.java.Movimentacao;
import EstacionamentoNegocio.java.Vagas;
import EstacionamentoNegocio.java.Veiculo;
import EstacionamentoUtilitario.java.EstacionamentoUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class DAOEstacionamento {

    public static Connection getConnection() throws SQLException {
        String url = EstacionamentoUtil.get("url");
        String usuario = EstacionamentoUtil.get("usuario");
        String senha = EstacionamentoUtil.get("senha");
        return DriverManager.getConnection(url, usuario, senha);
    }

    public static void closeConnection(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void criar(Movimentacao mov) {
        String sqlMov = EstacionamentoUtil.get("insertMov");
        String sqlVaga = EstacionamentoUtil.get("atualizaVaga");
        Connection conexao = null;

        try {
            conexao = getConnection();
            conexao.setAutoCommit(false);

            PreparedStatement ps = conexao.prepareStatement(sqlMov);
            ps.setString(1, mov.getVeiculo().getPlaca());
            ps.setString(2, mov.getVeiculo().getMarca());
            ps.setString(3, mov.getVeiculo().getModelo());
            ps.setString(4, mov.getVeiculo().getCor());
            ps.setTimestamp(5, Timestamp.valueOf(mov.getDataEntrada()));
            ps.execute();

            ps = conexao.prepareStatement(sqlVaga);
            ps.setInt(1, Vagas.ocupadas() + 1);
            ps.execute();

            conexao.commit();

        } catch (SQLException e) {
            try {
                e.printStackTrace();
                conexao.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            closeConnection(conexao);
        }
    }

    public Movimentacao buscarMovimentacaoAberta(String placa) {
        String sql = EstacionamentoUtil.get("buscarMovAberta");
        Connection conexao = null;
        Movimentacao mov = null;

        try {
            conexao = getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, placa);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Veiculo veiculo = new Veiculo(
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("cor")
                );

                mov = new Movimentacao(
                        veiculo,
                        rs.getTimestamp("data_entrada").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conexao);
        }

        return mov;
    }

    public void atualizar(Movimentacao mov) {
        String sqlMov = EstacionamentoUtil.get("updateMov");
        String sqlVaga = EstacionamentoUtil.get("atualizaVaga");
        Connection conexao = null;

        try {
            conexao = getConnection();
            conexao.setAutoCommit(false);

            // ✅ ALTERAÇÃO ESSENCIAL
            EstacionamentoUtil.calcularValorPago(mov);

            PreparedStatement ps = conexao.prepareStatement(sqlMov);
            ps.setDouble(1, mov.getValor());
            ps.setTimestamp(2, Timestamp.valueOf(mov.getDataSaida()));
            ps.setString(3, mov.getVeiculo().getPlaca());
            ps.execute();

            ps = conexao.prepareStatement(sqlVaga);
            ps.setInt(1, Vagas.ocupadas() - 1);
            ps.execute();

            conexao.commit();

        } catch (SQLException e) {
            try {
                e.printStackTrace();
                conexao.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            closeConnection(conexao);
        }
    }

    public List<Movimentacao> consultarMovimentacoes(LocalDateTime data) {
        Connection conexao = null;
        String sql = EstacionamentoUtil.get("selectMovRelatorio");
        List<Movimentacao> movimentacoes = new ArrayList<>();

        try {
            conexao = getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setTimestamp(1, Timestamp.valueOf(data));

            LocalDateTime dataFim = data
                    .with(TemporalAdjusters.lastDayOfMonth())
                    .withHour(23).withMinute(59).withSecond(59);

            ps.setTimestamp(2, Timestamp.valueOf(dataFim));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String placa = rs.getString("placa");
                LocalDateTime entrada = rs.getTimestamp("data_entrada").toLocalDateTime();
                LocalDateTime saida = rs.getTimestamp("data_saida").toLocalDateTime();
                double valor = rs.getDouble("valor");

                Veiculo veiculo = new Veiculo(placa);
                Movimentacao mov = new Movimentacao(veiculo, entrada);
                mov.setDataSaida(saida);
                mov.setValor(valor);

                movimentacoes.add(mov);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conexao);
        }

        return movimentacoes;
    }

    public int getOcupadas() {
        int ocupadas = 0;
        String sql = EstacionamentoUtil.get("consultaOcupadas");
        Connection conexao = null;

        try {
            conexao = getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ocupadas = rs.getInt("ocupadas");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conexao);
        }

        return ocupadas;
    }
}
