package application.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
	public String formatarDataSemHora(LocalDateTime data) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return data.format(formato);
	}
	

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public String converterFormatoData(LocalDateTime data) {
		return data.format(formatter);
	}

	public String converterFormatoData(LocalDate data) {
		return data.format(dateFormatter);
	}

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

		// Se não houver tempo de almoço, retorna a duração total da jornada
		if (duracaoAlmoco == null || duracaoAlmoco.equals(Duration.ZERO)) {
			return duracaoJornada;
		} else {
			// Subtrai a duração do almoço e o tempo padrão da duração total da jornada
			Duration jornada = duracaoJornada.minus(duracaoAlmoco).minus(tempoPadrao);

			// Retorna a diferença calculada
			return jornada;
		}
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

	

}
