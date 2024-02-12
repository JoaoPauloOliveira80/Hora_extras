package application.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatUtils {

	public  Duration calcularTotalHora(Duration duracaoJornada, Duration duracaoAlmoco) {
		Duration tempoPadrao = Duration.ofHours(8).plusMinutes(48);

		if (duracaoAlmoco == null || duracaoAlmoco.equals(Duration.ZERO)) {
			return duracaoJornada;
		} else {
			Duration jornada = duracaoJornada.minus(duracaoAlmoco).minus(tempoPadrao);
			return jornada.isNegative() ? Duration.ZERO : jornada;
		}
	}

	public  String formatarData(LocalDateTime data) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
		return data.format(formato);
	}

	public  String formatarDuration(Duration duracao) {
		long horas = duracao.toHours();
		int minutos = duracao.toMinutesPart();
		return String.format("%02d:%02d", horas, minutos);
	}
	
	public  String formatarDataSemHora(LocalDateTime data) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return data.format(formato);
	}

	public  String converterFormatoData(LocalDateTime data) {
		DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return data.format(formatoSaida);
	}
}
