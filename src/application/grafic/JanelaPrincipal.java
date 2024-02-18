package application.grafic;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import application.controller.JornadaController;
import application.model.Centralizar;
import application.model.Jornada;
import application.utils.Utils;

public class JanelaPrincipal extends JFrame {
	private Utils utils = new Utils();
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JornadaController jornadaController;
	private int porcentagem;
	private Duration hrExtradiaria;
	private Duration cem70 = Duration.ZERO;
	private Duration cem110 = Duration.ZERO;
	private static JanelaPrincipal instance;

	@SuppressWarnings("serial")
	public JanelaPrincipal(JornadaController jornadaController, LocalDate dataInicio, LocalDate dataFim) {

		setTitle("Controle de Jornada - " + Utils.converterFormatoData(dataInicio) + " a "
				+ Utils.converterFormatoData(dataFim));

		this.jornadaController = jornadaController;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1093, 529);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		model = new DefaultTableModel(new Object[] { "Dia trabalhado", "Data", "Início da Jornada", "Fim da Jornada",
				"Início do Almoço", "Fim do Almoço", "Hora diária", "Porcentagem" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				 return false;
			}
		};
		table = new JTable(model);
		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);

		table.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		            int row = table.rowAtPoint(e.getPoint());
		            int col = table.columnAtPoint(e.getPoint());

		            System.out.println("Linha clicada: " + row);
		            System.out.println("Coluna clicada: " + col);

		            // Verifique se a linha clicada não está entre as últimas três
		            if (row < table.getRowCount() - 3 && col != 0 && col != 6) {
		                // Obtenha o valor da célula clicada
		                Object cellValue = table.getValueAt(row, col);
		                String escolhido = "";
		                switch (col) {
		                case 1:
		                    escolhido = "Digite a nova data (formato DD/MM/YYYY):";
		                    break;
		                case 2:
		                    escolhido = "Digite a hora de início da jornada (formato HH:MM):";
		                    break;
		                case 3:
		                    escolhido = "Digite a hora de fim da jornada (formato HH:MM):";
		                    break;
		                case 4:
		                    escolhido = "Digite a hora de início do almoço (formato HH:MM):";
		                    break;
		                case 5:
		                    escolhido = "Digite a hora de fim do almoço (formato HH:MM):";
		                    break;
		                case 7:
		                    escolhido = "Digite a porcentagem (70, 110 ou 0):";
		                    break;
		                // Adicione mais casos conforme necessário
		                }

		                // Exiba o valor da célula em um JOptionPane para edição
		                String novoValor = JOptionPane.showInputDialog(escolhido, cellValue);
		                if (novoValor != null) {
		                    // Validação de entrada
		                    if ((col >= 1 && col <= 5) && !novoValor.matches("\\d{2}/\\d{2}/\\d{4}")
		                            && !novoValor.matches("\\d{2}:\\d{2}")) {
		                        JOptionPane.showMessageDialog(null,
		                                "Por favor, insira a data ou hora no formato correto (DD/MM/YYYY ou HH:MM).");
		                    } else if (col == 7
		                            && !(novoValor.equals("70") || novoValor.equals("110") || novoValor.equals("0"))) {
		                        JOptionPane.showMessageDialog(null,
		                                "Por favor, insira o valor da porcentagem 70 ou 110.");
		                    } else {
		                        // Atualize o valor na célula
		                        table.setValueAt(novoValor, row, col);
		                    }
		                }
		            }
		        }
		    }
		});




		DefaultTableCellRenderer centralizar = new Centralizar();
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centralizar);
		}
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 52, 1054, 326);
		contentPane.add(scrollPane);

		// Cria uma instância da classe Utils
		Utils utils = new Utils();

		// Cria o primeiro JDateChooser
		// Cria o primeiro JDateChooser
		JDateChooser dateChooser1 = utils.createFormattedDateChooser("##/##/####");
		((JTextFieldDateEditor) dateChooser1.getDateEditor()).setEditable(false);
		dateChooser1.setPreferredSize(new Dimension(150, 30));
		dateChooser1.setBounds(10, 11, 150, 30);
		contentPane.add(new JLabel("Data Inicial:"));
		contentPane.add(dateChooser1);

		// Cria o segundo JDateChooser
		JDateChooser dateChooser2 = utils.createFormattedDateChooser("##/##/####");
		((JTextFieldDateEditor) dateChooser2.getDateEditor()).setEditable(false);
		dateChooser2.setPreferredSize(new Dimension(150, 30));
		dateChooser2.setBounds(180, 11, 150, 30);
		contentPane.add(new JLabel("Data Final:"));
		contentPane.add(dateChooser2);


		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBounds(340, 11, 127, 30);
		btnPesquisar.setPreferredSize(new Dimension(150, 50));

		btnPesquisar.addActionListener(e -> {
			if (!utils.validarDatasSelecionadas(dateChooser1, dateChooser2)) {
				return;
			}

			LocalDate startDate = dateChooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate endDate = dateChooser2.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (startDate.isAfter(endDate)) {
				JOptionPane.showMessageDialog(null, "A data inicial não pode ser posterior à data final.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			LocalDate dataAtual = LocalDate.now();

			@SuppressWarnings("unused")
			boolean mesAnterior = startDate.isBefore(dataAtual)
					&& startDate.getMonthValue() == dataAtual.getMonthValue();

			listar(startDate, endDate);

			setTitle("Controle de Jornada - " + Utils.converterFormatoData(startDate) + " a "
					+ Utils.converterFormatoData(endDate));

		});
		contentPane.add(btnPesquisar);

		JButton btnCadastrar = new JButton("CADASTRAR");
		btnCadastrar.setBounds(79, 389, 127, 90);
		contentPane.add(btnCadastrar);

		JButton btnAtualizar = new JButton("ATUALIZAR");
		btnAtualizar.setBounds(236, 389, 127, 90);
		contentPane.add(btnAtualizar);
	}

	String msg = "";
	boolean hr70 = false;
	boolean hr110 = false;

	public void listar(LocalDate dataInicio, LocalDate dataFim) {
		model.setRowCount(0);
		LocalDateTime datJornada;
		Duration duracaoJornada = Duration.ZERO;
		Duration duracaoAlmoco = Duration.ZERO;
		int cont = 0;

		cem70 = Duration.ZERO;
		cem110 = Duration.ZERO;

		List<Jornada> jornadas = jornadaController.ListaPeriodo(dataInicio.atStartOfDay(), dataFim.atTime(23, 59));

		for (Jornada jornada : jornadas) {
			cont++;
			LocalDateTime startJornada;
			LocalDateTime endJornada;
			LocalDateTime startAlmoco;
			LocalDateTime endAlmoco;

			datJornada = jornada.getEndJornada();
			startJornada = jornada.getStartJornada();
			endJornada = jornada.getEndJornada();
			startAlmoco = jornada.getStartRefeicao();
			endAlmoco = jornada.getEndRefeicao();
			porcentagem = jornada.getPorcentagem();

			duracaoJornada = Duration.between(startJornada, endJornada);
			duracaoAlmoco = Duration.between(startAlmoco, endAlmoco);

			duracaoAlmoco = duracaoAlmoco.compareTo(duracaoJornada) > 0 ? duracaoJornada : duracaoAlmoco;

			hrExtradiaria = utils.calcularTotalHora(duracaoJornada, duracaoAlmoco);

			if (!jornadaAdicionada(jornada)) {
				model.addRow(
						new Object[] { cont, utils.converterFormatoData(datJornada), utils.formatarData(startJornada),
								utils.formatarData(endJornada), utils.formatarData(startAlmoco),
								utils.formatarData(endAlmoco), utils.formatarDuration(hrExtradiaria), porcentagem });
			}

			if (porcentagem == 70) {
				hr70 = true;
				cem70 = cem70.plus(hrExtradiaria);
			}

			if (porcentagem == 110) {
				hr110 = true;
				cem110 = cem110.plus(hrExtradiaria);
			}
		}

		int rowCount = table.getRowCount();
		System.out.println("Número de linhas: " + rowCount);

		model.addRow(new Object[] { "**********************", "**********************", "**********************",
				"**********************", "**********************", "**********************", "**********************",
				"**********************" });

		if (hr70) {
			model.addRow(new Object[] { "------------", "------------", "------------", "------------", "------------",
					"Hora Mensal   70%", utils.formatarDuration(cem70), "------------" });
		}

		if (hr110) {
			model.addRow(new Object[] { "------------", "------------", "------------", "------------", "------------",
					"Hora Mensal 110%", utils.formatarDuration(cem110), "------------" });
		}

		((DefaultTableModel) table.getModel()).fireTableDataChanged();
	}

	private boolean jornadaAdicionada(Jornada jornada) {
		int rowCount = model.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			LocalDateTime existingDatJornada = Utils.converterFormatoData((String) model.getValueAt(i, 1));
			if (existingDatJornada.equals(jornada.getEndJornada())) {
				return true;
			}
		}
		return false;
	}

}
