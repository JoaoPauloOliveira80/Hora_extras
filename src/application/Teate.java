package application;

public class Teate {

}
//package application;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//import application.dao.JornadaDAO;
//import application.model.Jornada;
//
//public class JornadaService {
//
//	private JornadaDAO jornadaDAO;
//
//	public JornadaService(JornadaDAO jornadaDAO) {
//		this.jornadaDAO = jornadaDAO;
//	}
//
//	Duration hrTotalMes = null;
//
//	public void getListaPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
//	    List<Jornada> jornadas = jornadaDAO.listarPorPeriodo(dataInicio, dataFim);
//	    LocalDateTime diaTrabalho;
//	    LocalDateTime startJornada;
//	    LocalDateTime endJornada;
//	    LocalDateTime startAlmoco;
//	    LocalDateTime endAlmoco;
//	    int porcentagem;
//
//	    Duration hrTotalMes70 = Duration.ZERO;
//	    Duration hrTotalMes110 = Duration.ZERO;
//	    Duration jornadaTotal  =  Duration.ZERO;
//
//	    Duration totalJornada70 = Duration.ZERO;
//	    Duration totalJornada110 = Duration.ZERO;
//	    Duration duracaoJornada =  Duration.ZERO;
//	    Duration duracaoAlmoco =  Duration.ZERO;
//	    
//	    boolean has70 = false;
//	    boolean has110 = false;
//
//	    for (Jornada jornada : jornadas) {
//	        porcentagem = jornada.getPorcentagem();
//
//	        if(porcentagem == 70) {
//	        	
//	        	has70 = true;
//	            diaTrabalho = jornada.getEndJornada();
//	            startJornada = jornada.getStartJornada();
//	            endJornada = jornada.getEndJornada();
//	            startAlmoco = jornada.getStartRefeicao();
//	            endAlmoco =  jornada.getEndRefeicao();
//
//	           
//
//	            duracaoJornada = Duration.between(startJornada, endJornada);
//	            duracaoAlmoco = Duration.between(startAlmoco, endAlmoco);          
//	            jornadaTotal = duracaoJornada.minus(duracaoAlmoco);
//	            
//	            
//	            Duration hora = calcularTotalHoraExtraDoMes(jornadas);
//	            hrTotalMes70 = hrTotalMes70.plus(hora);
//	            
//	            Duration tempoPadrao = Duration.ofHours(8).plusMinutes(48);
//	            totalJornada70 = totalJornada70.plus(tempoPadrao);
//	           
//      
//	            
//	            listarJornada(diaTrabalho, endAlmoco, startJornada, endJornada, startAlmoco, endAlmoco, porcentagem,totalJornada70 );
//	        }
//	    }
//
//	    for (Jornada jornada : jornadas) {
//	        porcentagem = jornada.getPorcentagem();
//
//	        if(porcentagem == 110) {
//	        	has110 = true;
//	            diaTrabalho = jornada.getEndJornada();
//	            startJornada = jornada.getStartJornada();
//	            endJornada = jornada.getEndJornada();
//	            startAlmoco = jornada.getStartRefeicao();
//	            endAlmoco =  jornada.getEndRefeicao();
//
//	            Duration hora = calcularTotalHoraExtraDoMes(jornadas);
//	            hrTotalMes110 = hrTotalMes110.plus(hora);
//
//	            duracaoJornada = Duration.between(startJornada, endJornada);
//	            duracaoAlmoco = Duration.between(startAlmoco, endAlmoco);          
//	            jornadaTotal = duracaoJornada.minus(duracaoAlmoco);
//
//	            totalJornada110 = totalJornada110.plus(jornadaTotal);
//    
//
//	            listarJornada(diaTrabalho, endAlmoco, startJornada, endJornada, startAlmoco, endAlmoco, porcentagem,totalJornada110 );
//	        }
//	    }
//
//	    if(has70) {
//		    System.out.println("Total de Hrs extras 70%: " + (totalJornada70 != null ? formatarDuracao(totalJornada70) : "N/A"));
//
//	    }
//	    
//	    if(has110) {
//		    System.out.println("Total de Hrs extras 110%: " + (totalJornada110 != null ? formatarDuracao(totalJornada110) : "N/A"));
//
//	    }
//	}
//
//
//
//	public List<Jornada> listarTodasJornadas() {
//		return jornadaDAO.listarAll();
//	}
//
//	private void listarJornada(LocalDateTime diaTrabalho, LocalDateTime dataEntrada, LocalDateTime startJornada,
//			LocalDateTime endJornada, LocalDateTime startAlmoco, LocalDateTime endAlmoco, int porcentagem, Duration totalJornada) {
//
//		
//		Duration duracao = calcularDiferencaHoras(startJornada, endJornada);
//		
//		
//		
//
//		System.out.println(" Data: " + (diaTrabalho != null ? formatarData(diaTrabalho) : "N/A"));
//
//		System.out.println(" Entrada jornada: " + (startJornada != null ? formatarHoraMinSeg(startJornada) : "N/A"));
//		System.out.println(" Saída jornada: " + (endJornada != null ? formatarHoraMinSeg(endJornada) : "N/A"));
//		System.out.println(" Entrada almoço: " + (startAlmoco != null ? formatarHoraMinSeg(startAlmoco) : "N/A"));
//		System.out.println(" Retorno almoço: " + (endAlmoco != null ? formatarHoraMinSeg(endAlmoco) : "N/A"));
//
//		
//		System.out.println(" Hora Extras diária: " + (totalJornada != null ? formatarDuracao(totalJornada) : "N/A"));
//		System.out.println(" Hora feita a: " + porcentagem + "%");
//
//		System.out.println();
//
//	}
//
//	public static String formatarHoraMinSeg(LocalDateTime dataJornada) {
//		// Escolha o formato desejado. Aqui, estou usando o formato "HH:mm:ss"
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//
//		// Formate a data usando o DateTimeFormatter
//		String dataFormatada = dataJornada.format(formatter);
//
//		return dataFormatada;
//	}
//
//	public static String formatarData(LocalDateTime dataJornada) {
//		// Escolha o formato desejado. Aqui, estou usando o formato "dd/MM/yyyy"
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//		// Formate a data usando o DateTimeFormatter
//		String dataFormatada = dataJornada.format(formatter);
//
//		return dataFormatada;
//	}
//
//	public Duration calcularTotalHoraExtraDoMes(List<Jornada> jornadas, int porcentagem) {
//		Duration total = Duration.ZERO;
//		for (Jornada jornada : jornadas) {
//			if (jornada.getPorcentagem() == porcentagem) {
//				LocalDateTime startJornada = jornada.getStartJornada();
//				LocalDateTime endJornada = jornada.getEndJornada();
//				LocalDateTime startAlmoco = (jornada.getStartRefeicao() != null) ? jornada.getStartRefeicao()
//						: LocalDateTime.of(2000, 1, 1, 0, 0);
//				LocalDateTime endAlmoco = (jornada.getEndRefeicao() != null) ? jornada.getEndRefeicao()
//						: LocalDateTime.of(2000, 1, 1, 0, 0);
//				total = total.plus(calcularHoraDia(startJornada, endJornada, startAlmoco, endAlmoco));
//			}
//		}
//		return total;
//	}
//
//	public static Duration calcularTotalHoraExtraDoMes(List<Jornada> jornadas) {
//		Duration totalHoraExtra = Duration.ZERO;
//
//		for (Jornada jornada : jornadas) {
//			Duration horaExtra = calcularHoraDia(jornada.getStartJornada(), jornada.getEndJornada(),
//					jornada.getStartRefeicao(), jornada.getEndRefeicao());
//			totalHoraExtra = totalHoraExtra.plus(horaExtra);
//
//		}
//
//		return totalHoraExtra;
//	}
//
//	public static Duration calcularHoraDia(LocalDateTime inicioJornada, LocalDateTime fimJornada,
//			LocalDateTime inicioAlmoco, LocalDateTime fimAlmoco) {
//
//		Duration tempoJornada = (inicioJornada != null && fimJornada != null)
//				? Duration.between(inicioJornada, fimJornada)
//				: Duration.ZERO;
//		Duration tempoAlmoco = (inicioAlmoco != null && fimAlmoco != null) ? Duration.between(inicioAlmoco, fimAlmoco)
//				: Duration.ZERO;
//
//		// Define o tempo de trabalho padrão
//		Duration tempoPadrao = Duration.ofHours(8).plusMinutes(48);
//
//		// Subtrai o tempo de almoço do tempo total da jornada
//		Duration tempoTrabalhado = tempoJornada.minus(tempoAlmoco);
//
//		Duration totalHoraExtra = tempoTrabalhado.minus(tempoPadrao);
//
//		return totalHoraExtra.isNegative() ? Duration.ZERO : totalHoraExtra;
//	}
//
//	public static Duration calcularDiferencaHoras(LocalDateTime inicio, LocalDateTime fim) {
//		Duration duracao = Duration.between(inicio, fim);
//
//		return duracao;
//	}
//
//	public static String formatarDuration(Duration duracao) {
//		long horas = duracao.toHours();
//		int minutos = duracao.toMinutesPart();
//		return String.format("%02d:%02d", horas, minutos);
//	}
//
//	public static String formatarDuracao(Duration duracao) {
//		if (duracao == null) {
//			return "N/A";
//		}
//		long horas = duracao.toHours();
//		int minutos = duracao.toMinutesPart();
//		return String.format("%02d:%02d", horas, minutos);
//	}
//
//}

