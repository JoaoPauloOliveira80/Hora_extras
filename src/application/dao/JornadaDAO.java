package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import application.model.Jornada;

public class JornadaDAO {

	public List<Jornada> listarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
		String sql = "SELECT * FROM jornadastrabalhocopia WHERE startJornada BETWEEN ? AND ? ORDER BY startJornada ASC";

	    List<Jornada> lista = new ArrayList<>();

	    Connection conn = null;
	    PreparedStatement pstm = null;
	    ResultSet rs = null;

	    try {
	        conn = (Connection) ConnectionDB.create();
	        pstm = (PreparedStatement) conn.prepareStatement(sql);

	        // Definindo os parâmetros da consulta
	        pstm.setTimestamp(1, Timestamp.valueOf(dataInicio));
	        pstm.setTimestamp(2, Timestamp.valueOf(dataFim));

	        rs = pstm.executeQuery();

	        while (rs.next()) {
	            Jornada jornada = new Jornada();

	            // Verifica se o valor não é nulo antes de chamar toLocalDateTime()
	            Timestamp startJornadaTimestamp = rs.getTimestamp("startJornada");
	            if (startJornadaTimestamp != null) {
	                jornada.setStartJornada(startJornadaTimestamp.toLocalDateTime());
	            }

	            Timestamp endJornadaTimestamp = rs.getTimestamp("endJornada");
	            if (endJornadaTimestamp != null) {
	                jornada.setEndJornada(endJornadaTimestamp.toLocalDateTime());
	            }

	            Timestamp startAlmocoTimestamp = rs.getTimestamp("startAlmoco");
	            if (startAlmocoTimestamp != null) {
	                jornada.setStartRefeicao(startAlmocoTimestamp.toLocalDateTime());
	            }

	            Timestamp endAlmocoTimestamp = rs.getTimestamp("endAlmoco");
	            if (endAlmocoTimestamp != null) {
	                jornada.setEndRefeicao(endAlmocoTimestamp.toLocalDateTime());
	            }

	            jornada.setPorcentagem(rs.getInt("porcentagem"));

	            lista.add(jornada);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstm != null) {
	                pstm.close();
	            }

	            if (conn != null) {
	                conn.close();
	            }

	            if (rs != null) {
	                rs.close();
	            }
	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
	    }

	    return lista;
	}
	
	public void update(Jornada jornada) {
	    String sql = "UPDATE jornadastrabalhocopia SET startJornada=?, endJornada=?, startAlmoco=?, endAlmoco=?, porcentagem=? WHERE id=?";

	    Connection conn = null;
	    PreparedStatement pstm = null;

	    try {
	        conn = ConnectionDB.create();
	        pstm = conn.prepareStatement(sql);

	        // Define os parâmetros para a atualização
	        pstm.setTimestamp(1, Timestamp.valueOf(jornada.getStartJornada()));
	        pstm.setTimestamp(2, Timestamp.valueOf(jornada.getEndJornada()));
	        pstm.setTimestamp(3, Timestamp.valueOf(jornada.getStartRefeicao()));
	        pstm.setTimestamp(4, Timestamp.valueOf(jornada.getEndRefeicao()));
	        pstm.setInt(5, jornada.getPorcentagem());
	        pstm.setInt(6, jornada.getId()); // Supondo que você tenha um campo "id" na sua tabela

	        // Executa a atualização
	        pstm.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstm != null) {
	                pstm.close();
	            }

	            if (conn != null) {
	                conn.close();
	            }
	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
	    }
	}



	
    public List<Jornada> listarTodas() {
        List<Jornada> jornadas = new ArrayList<>();
        String sql = "SELECT * FROM JornadasTrabalho order by startJornada asc";
        
        try (Connection conn = ConnectionDB.create();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                Jornada jornada = new Jornada();

                // Verifica se o valor não é nulo antes de chamar toLocalDateTime()
                Timestamp startJornadaTimestamp = rs.getTimestamp("startJornada");
                if (startJornadaTimestamp != null) {
                    jornada.setStartJornada(startJornadaTimestamp.toLocalDateTime());
                }

                Timestamp endJornadaTimestamp = rs.getTimestamp("endJornada");
                if (endJornadaTimestamp != null) {
                    jornada.setEndJornada(endJornadaTimestamp.toLocalDateTime());
                }

                Timestamp startAlmocoTimestamp = rs.getTimestamp("startAlmoco");
                if (startAlmocoTimestamp != null) {
                    jornada.setStartRefeicao(startAlmocoTimestamp.toLocalDateTime());
                }

                Timestamp endAlmocoTimestamp = rs.getTimestamp("endAlmoco");
                if (endAlmocoTimestamp != null) {
                    jornada.setEndRefeicao(endAlmocoTimestamp.toLocalDateTime());
                }

                jornada.setPorcentagem(rs.getInt("porcentagem"));

                jornadas.add(jornada);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return jornadas;
    }



}
