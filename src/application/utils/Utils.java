package application.utils;

import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

public class Utils {

	public Duration calcularTotalHora(Duration duracaoJornada, Duration duracaoAlmoco) {
		Duration tempoPadrao = Duration.ofHours(8).plusMinutes(48);

		if (duracaoAlmoco == null || duracaoAlmoco.equals(Duration.ZERO)) {
			return duracaoJornada;
		} else {
			Duration jornada = duracaoJornada.minus(duracaoAlmoco).minus(tempoPadrao);
			return jornada.isNegative() ? Duration.ZERO : jornada;
		}
	}

//	public Duration calcularTotalHora(Duration duracaoJornada, Duration duracaoAlmoco) {
//		Duration tempoPadrao = Duration.ofHours(8).plusMinutes(48);
//
//		// Se não houver tempo de almoço, retorna a duração total da jornada
//		if (duracaoAlmoco == null || duracaoAlmoco.equals(Duration.ZERO)) {
//			return duracaoJornada;
//		} else {
//			// Subtrai a duração do almoço e o tempo padrão da duração total da jornada
//			Duration jornada = duracaoJornada.minus(duracaoAlmoco).minus(tempoPadrao);
//
//			// Retorna a diferença calculada
//			return jornada;
//		}
//	}

	public  static String converterFormatoData(LocalDate data) {
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

	public boolean validarDatasSelecionadas(JDateChooser dateChooser1, JDateChooser dateChooser2) {
		if (dateChooser1.getDate() == null || dateChooser2.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Por favor, selecione ambas as datas.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public static LocalDateTime converterFormatoData(String data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(data, formatter).atStartOfDay();
	}


	public void MascaraData(JDateChooser dateChooser, String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		dateChooser.setDateFormatString(formato);
		dateChooser.setDate(new Date()); // Define a data inicial (opcional)
	}

	public JDateChooser createFormattedDateChooser(String mask) {
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd/MM/yyyy");

		JFormattedTextField formattedTextField = createMaskedTextField(mask);

		dateChooser.addPropertyChangeListener("date", evt -> {
			if ("date".equals(evt.getPropertyName())) {
				Object newValue = evt.getNewValue();
				if (newValue instanceof java.util.Date) {
					formattedTextField.setValue(new SimpleDateFormat("dd/MM/yyyy").format(newValue));
				}
			}
		});

		JPanel panel = new JPanel();
		panel.add(formattedTextField);
		panel.add(dateChooser);

		return dateChooser;
	}

	private JFormattedTextField createMaskedTextField(String mask) {
		MaskFormatter maskFormatter = null;
		try {
			maskFormatter = new MaskFormatter(mask);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new JFormattedTextField(maskFormatter);
	}

	// Dentro da classe Utils
	public static void abrirJanelaEdicao(DefaultTableModel model, int row, int column) {
	    // Certifique-se de que uma linha válida foi clicada
	    if (row >= 0 && row < model.getRowCount()) {
	        LocalDateTime datJornadaDateTime = converterFormatoData((String) model.getValueAt(row, 1));
	        LocalTime startJornadaTime = LocalTime.parse((String) model.getValueAt(row, 2), DateTimeFormatter.ofPattern("HH:mm"));
	        LocalTime endJornadaTime = LocalTime.parse((String) model.getValueAt(row, 3), DateTimeFormatter.ofPattern("HH:mm"));
	        LocalTime startAlmocoTime = LocalTime.parse((String) model.getValueAt(row, 4), DateTimeFormatter.ofPattern("HH:mm"));
	        LocalTime endAlmocoTime = LocalTime.parse((String) model.getValueAt(row, 5), DateTimeFormatter.ofPattern("HH:mm"));
	        int porcentagem = (int) model.getValueAt(row, 7);

	        // Defina as mensagens e inputs conforme a coluna clicada
	        String mensagem = "";
	        String novoDado = "";
	        switch (column) {
	            case 2:
	                mensagem = "Novo Início da Jornada (HH:mm):";
	                novoDado = JOptionPane.showInputDialog(mensagem);
	                if (novoDado != null) {
	                    LocalTime novoStartJornadaTime = LocalTime.parse(novoDado, DateTimeFormatter.ofPattern("HH:mm"));
	                    // Atualize o valor na tabela ou no objeto Jornada
	                    model.setValueAt(formatarHora(LocalDateTime.of(datJornadaDateTime.toLocalDate(), novoStartJornadaTime)), row, column);
	                }
	                break;
	            case 3:
	                // Repita o processo para outras colunas, se necessário
	                break;
	            // Adicione outros casos conforme necessário
	            default:
	                // Coluna não suportada
	                break;
	        }

	        // Atualize a tabela após a edição
	        model.fireTableDataChanged();
	    }
	}



	// Função auxiliar para mostrar um JOptionPane com um botão "Cancelar"
	private static String showInputDialogWithCancel(String mensagem) {
	    Object[] options = {"OK", "Cancelar"};
	    int result = JOptionPane.showOptionDialog(null, mensagem, "Editar",
	            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

	    if (result == 0) {
	        // Usuário pressionou "OK", então mostre a caixa de diálogo de entrada padrão
	        return JOptionPane.showInputDialog(mensagem);
	    }

	    // Usuário pressionou "Cancelar" ou fechou a caixa de diálogo, retorne null
	    return null;
	}


	public static String formatarHora(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        return dateTime.format(formatter);
    }

	
	public static JFormattedTextField createDateField() {
        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            JFormattedTextField dateField = new JFormattedTextField(mask);
            dateField.setPreferredSize(new Dimension(150, 30));
            return dateField;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
