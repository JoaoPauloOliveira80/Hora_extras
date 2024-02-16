package application.utils;

import java.awt.Color;
import java.awt.Component;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.toedter.calendar.JDateChooser;

public class Utils {

//	public  Duration calcularTotalHora(Duration duracaoJornada, Duration duracaoAlmoco) {
//		Duration tempoPadrao = Duration.ofHours(8).plusMinutes(48);
//
//		if (duracaoAlmoco == null || duracaoAlmoco.equals(Duration.ZERO)) {
//			return duracaoJornada;
//		} else {
//			Duration jornada = duracaoJornada.minus(duracaoAlmoco).minus(tempoPadrao);
//			return jornada.isNegative() ? Duration.ZERO : jornada;
//		}
//	}

	public Duration calcularTotalHora(Duration duracaoJornada, Duration duracaoAlmoco) {
		Duration tempoPadrao = Duration.ofHours(8).plusMinutes(48);

		// Se n�o houver tempo de almo�o, retorna a dura��o total da jornada
		if (duracaoAlmoco == null || duracaoAlmoco.equals(Duration.ZERO)) {
			return duracaoJornada;
		} else {
			// Subtrai a dura��o do almo�o e o tempo padr�o da dura��o total da jornada
			Duration jornada = duracaoJornada.minus(duracaoAlmoco).minus(tempoPadrao);

			// Retorna a diferen�a calculada
			return jornada;
		}
	}

	public String converterFormatoData(LocalDate data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return data.format(formatter);
	}

	public String formatarData(LocalDateTime data) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
		return data.format(formato);
	}

	public String formatarDuration(Duration duracao) {
		long horas = duracao.toHours();
		int minutos = duracao.toMinutesPart();
		return String.format("%02d:%02d", horas, minutos);
	}

	public String formatarDataSemHora(LocalDateTime data) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return data.format(formato);
	}

	public String converterFormatoData(LocalDateTime data) {
		DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return data.format(formatoSaida);
	}

	
	public  boolean validarDatasSelecionadas(JDateChooser dateChooser1, JDateChooser dateChooser2) {
	    if (dateChooser1.getDate() == null || dateChooser2.getDate() == null) {
	        JOptionPane.showMessageDialog(null, "Por favor, selecione ambas as datas.", "Erro", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }
	    return true;
	}
	
	public LocalDateTime converterFormatoData(String data) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    return LocalDate.parse(data, formatter).atStartOfDay();
	}

	//COLORI AS LINHA DA TABLE
	// M�todo para obter um renderizador de linhas alternadas
    public DefaultTableCellRenderer getRenderizadorLinhasAlternadas() {
        return new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Alterna a cor de fundo com base no n�mero da linha
                if (row % 2 == 0) {
                    rendererComponent.setBackground(Color.WHITE);
                } else {
                    rendererComponent.setBackground(new Color(240, 240, 240)); // Cor alternativa
                }

                return rendererComponent;
            }
        };
    }
	
}
