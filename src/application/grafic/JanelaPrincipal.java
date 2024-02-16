package application.grafic;

import java.awt.Dimension;
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

import application.controller.JornadaController;
import application.model.Centralizar;
import application.model.Jornada;
import application.utils.Utils;

public class JanelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
	private Utils utils = new Utils();
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JornadaController jornadaController;
	private int porcentagem;
	private Duration hrExtradiaria;
	Duration cem70 = Duration.ZERO;
	Duration cem110 = Duration.ZERO;
	private static JanelaPrincipal instance;


	@SuppressWarnings("serial")
	public JanelaPrincipal(JornadaController jornadaController, LocalDate dataInicio, LocalDate dataFim) {
		
		
        
	

		setTitle("Controle de Jornada - " + utils.converterFormatoData(dataInicio) + " a "
				+ utils.converterFormatoData(dataFim));

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
		table.setDefaultRenderer(Object.class, utils.getRenderizadorLinhasAlternadas());
		table.getTableHeader().setReorderingAllowed(false);

		// ... (outros ajustes ou configurações necessárias)

		table.setEnabled(true);

		DefaultTableCellRenderer centralizar = new Centralizar();
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centralizar);
		}
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 52, 1054, 427);
		contentPane.add(scrollPane);

		// Criando o primeiro seletor de data
		JDateChooser dateChooser1 = utils.createFormattedDateChooser("##/##/####");
	//	utils.MascaraData(dateChooser1, "dd/MM/yy");
		dateChooser1.setPreferredSize(new Dimension(150, 30));
		dateChooser1.setBounds(10, 11, 150, 30);
		contentPane.add(new JLabel("Data Inicial:"));
		contentPane.add(dateChooser1);

		JDateChooser dateChooser2 = utils.createFormattedDateChooser("##/##/####");
		dateChooser2.setPreferredSize(new Dimension(150, 30));
		dateChooser2.setBounds(180, 11, 150, 30);
		contentPane.add(new JLabel("Data Final:"));
		contentPane.add(dateChooser2);

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBounds(340, 11, 127, 30);
		btnPesquisar.addActionListener(e -> {
		    // Validar datas antes de prosseguir
		    if (!utils.validarDatasSelecionadas(dateChooser1, dateChooser2)) {
		        return; // Saia do método se as datas estiverem vazias
		    }

		    // Converte as datas selecionadas para LocalDate
		    LocalDate startDate = dateChooser1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		    LocalDate endDate = dateChooser2.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		    // Verificar se a data inicial é posterior à data final
		    if (startDate.isAfter(endDate)) {
		        JOptionPane.showMessageDialog(null, "A data inicial não pode ser posterior à data final.", "Erro", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

		    // Verificar se as datas estão no mês anterior em relação à data atual
		    LocalDate dataAtual = LocalDate.now();
		    boolean mesAnterior = startDate.isBefore(dataAtual) && startDate.getMonthValue() == dataAtual.getMonthValue();

		    // Executa a pesquisa com as datas selecionadas
		    listar(startDate, endDate);

		        setTitle("Controle de Jornada - " + utils.converterFormatoData(startDate) + " a " + utils.converterFormatoData(endDate));
		    
		});
		contentPane.add(btnPesquisar);
	}

	String msg = "";
	boolean hr70 = false;
	boolean hr110 = false;

	// ...

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

		// Notificar a tabela sobre as mudanças
		((DefaultTableModel) table.getModel()).fireTableDataChanged();
	}

	private boolean jornadaAdicionada(Jornada jornada) {
		int rowCount = model.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			LocalDateTime existingDatJornada = utils.converterFormatoData((String) model.getValueAt(i, 1));
			if (existingDatJornada.equals(jornada.getEndJornada())) {
				return true;
			}
		}
		return false;
	}
	
	
	 public static JanelaPrincipal getInstance(JornadaController jornadaController, LocalDate dataInicio, LocalDate dataFim) {
	        if (instance == null) {
	            instance = new JanelaPrincipal(jornadaController, dataInicio, dataFim);
	        }
	        
	        if (instance != null && instance.isVisible()) {
	            // Se estiver aberta, traga-a para frente
	            instance.toFront();
	        }
	        return instance;
	    }
	 
	 
}
