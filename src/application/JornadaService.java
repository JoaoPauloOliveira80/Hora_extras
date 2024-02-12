package application;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import application.dao.JornadaDAO;
import application.model.Jornada;

public class JornadaService {

	private JornadaDAO jornadaDAO;

	public JornadaService(JornadaDAO jornadaDAO) {
		this.jornadaDAO = jornadaDAO;
	}

	public List<Jornada> listarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return jornadaDAO.listarPorPeriodo(dataInicio, dataFim);
    }
	
	
	public void getListaPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
		List<Jornada> jornadas = jornadaDAO.listarPorPeriodo(dataInicio, dataFim);
		LocalDateTime diaTrabalho;
		LocalDateTime startJornada;
		LocalDateTime endJornada;
		LocalDateTime startAlmoco;
		LocalDateTime endAlmoco;
		int porcentagem;

		Duration hrTotalMes70 = Duration.ZERO;

		Duration totalJornada110 = Duration.ZERO;
		Duration duracaoJornada = Duration.ZERO;
		Duration duracaoAlmoco = Duration.ZERO;
		Duration hrAcumulada = Duration.ZERO;

		boolean has70 = false;
		boolean has110 = false;

		for (Jornada jornada : jornadas) {
			porcentagem = jornada.getPorcentagem();

			if (porcentagem == 70) {
				has70 = true;
				diaTrabalho = jornada.getEndJornada();
				startJornada = jornada.getStartJornada();
				endJornada = jornada.getEndJornada();
				startAlmoco = jornada.getStartRefeicao();
				endAlmoco = jornada.getEndRefeicao();

				duracaoJornada = Duration.between(startJornada, endJornada);
				duracaoAlmoco = Duration.between(startAlmoco, endAlmoco);

				hrAcumulada = calcularTotalHora(duracaoJornada, duracaoAlmoco);

				hrTotalMes70 = hrTotalMes70.plus(hrAcumulada);

				listarJornada(diaTrabalho, endAlmoco, startJornada, endJornada, startAlmoco, endAlmoco, porcentagem,
						hrAcumulada);
			}
		}

		for (Jornada jornada : jornadas) {
			porcentagem = jornada.getPorcentagem();

			if (porcentagem == 110) {
				has110 = true;
				diaTrabalho = jornada.getEndJornada();
				startJornada = jornada.getStartJornada();
				endJornada = jornada.getEndJornada();
				startAlmoco = jornada.getStartRefeicao();
				endAlmoco = jornada.getEndRefeicao();

				duracaoJornada = Duration.between(startJornada, endJornada);
				duracaoAlmoco = Duration.between(startAlmoco, endAlmoco);

				hrAcumulada = calcularTotalHora(duracaoJornada, duracaoAlmoco);

				totalJornada110 = totalJornada110.plus(hrAcumulada);
				listarJornada(diaTrabalho, endAlmoco, startJornada, endJornada, startAlmoco, endAlmoco, porcentagem,
						hrAcumulada);
			}
		}

		if (has70) {
			System.out.println(
					"Total de Hrs extras 70%: " + (hrTotalMes70 != null ? formatarDuracao(hrTotalMes70) : "N/A"));
		}

		if (has110) {
			System.out.println("Total de Hrs extras 110%: "
					+ (totalJornada110 != null ? formatarDuracao(totalJornada110) : "N/A"));

		}
	}

	
	private void listarJornada(LocalDateTime diaTrabalho, LocalDateTime dataEntrada, LocalDateTime startJornada,
			LocalDateTime endJornada, LocalDateTime startAlmoco, LocalDateTime endAlmoco, int porcentagem,
			Duration totalHrExtra) {

		System.out.println(" Data: " + (diaTrabalho != null ? formatarData(diaTrabalho) : "N/A"));
		System.out.println(" Entrada jornada: " + (startJornada != null ? formatarHoraMinSeg(startJornada) : "N/A"));
		System.out.println(" Saída jornada: " + (endJornada != null ? formatarHoraMinSeg(endJornada) : "N/A"));
		System.out.println(" Entrada almoço: " + (startAlmoco != null ? formatarHoraMinSeg(startAlmoco) : "N/A"));
		System.out.println(" Retorno almoço: " + (endAlmoco != null ? formatarHoraMinSeg(endAlmoco) : "N/A"));

		System.out.println(" Hora Extras diária: " + (totalHrExtra != null ? formatarDuracao(totalHrExtra) : "N/A"));
		System.out.println(" Hora feita a: " + porcentagem + "%");

		System.out.println();

	}

	public static String formatarHoraMinSeg(LocalDateTime dataJornada) {
		// Escolha o formato desejado. Aqui, estou usando o formato "HH:mm:ss"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		// Formate a data usando o DateTimeFormatter
		String dataFormatada = dataJornada.format(formatter);

		return dataFormatada;
	}

	public static String formatarData(LocalDateTime dataJornada) {
		// Escolha o formato desejado. Aqui, estou usando o formato "dd/MM/yyyy"
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		// Formate a data usando o DateTimeFormatter
		String dataFormatada = dataJornada.format(formatter);

		return dataFormatada;
	}

	public static Duration calcularTotalHora(Duration duracaoJornada, Duration duracaoAlmoco) {
		Duration tempoPadrao = Duration.ofHours(8).plusMinutes(48);

		if (duracaoAlmoco == null || duracaoAlmoco.equals(Duration.ZERO)) {
			return duracaoJornada;
		} else {
			Duration jornada = duracaoJornada.minus(duracaoAlmoco).minus(tempoPadrao);
			return jornada.isNegative() ? Duration.ZERO : jornada;
		}
	}

	public static Duration calcularDiferencaHorasJornada(LocalDateTime inicio, LocalDateTime fim) {
		Duration duracao = Duration.between(inicio, fim);

		return duracao;
	}

	public static String formatarDuration(Duration duracao) {
		long horas = duracao.toHours();
		int minutos = duracao.toMinutesPart();
		return String.format("%02d:%02d", horas, minutos);
	}

	public static String formatarDuracao(Duration duracao) {
		if (duracao == null) {
			return "N/A";
		}
		long horas = duracao.toHours();
		int minutos = duracao.toMinutesPart();
		return String.format("%02d:%02d", horas, minutos);
	}

}
