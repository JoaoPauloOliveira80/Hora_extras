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
	    List<Jornada> jornadas = new ArrayList<>();
	    String sql = "SELECT * FROM JornadasTrabalhoCopia WHERE startJornada BETWEEN ? AND ? ORDER BY startJornada ASC";
	    
	    try (Connection conn = ConnectionDB.create();
	         PreparedStatement pstm = conn.prepareStatement(sql)) {

	        // Define os parâmetros da consulta
	        pstm.setTimestamp(1, Timestamp.valueOf(dataInicio));
	        pstm.setTimestamp(2, Timestamp.valueOf(dataFim));

	        ResultSet rs = pstm.executeQuery();

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
