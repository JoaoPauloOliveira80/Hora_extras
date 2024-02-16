package application.grafic;

import java.awt.Color;
import java.awt.Font;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import application.controller.JornadaController;
import application.model.Centralizar;
import application.model.Jornada;
import application.utils.Utils;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JanelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;
	private Utils utils = new Utils();
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JornadaController jornadaController;
	private JLabel txtHora70, txtHora110;
	private JLabel txtHora70_1;
	private int porcentagem;
	private Duration hrExtradiaria;
	
	

	@SuppressWarnings("serial")
	public JanelaPrincipal(JornadaController jornadaController, LocalDate dataInicio, LocalDate dataFim) {
        setTitle("Controle de Jornada - " + utils.converterFormatoData(dataInicio) +
                " a " + utils.converterFormatoData(dataFim));
        
        
		this.jornadaController = jornadaController;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(100, 100, 1000, 550);
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
		table.setEnabled(true);

		DefaultTableCellRenderer centralizar = new Centralizar();
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centralizar);
		}
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 964, 417);

		contentPane.add(scrollPane);

		txtHora70 = new JLabel("HORA EXTRA70%");
		txtHora70.setBounds(493, 474, 226, 37);
		txtHora70.setForeground(new Color(50, 205, 50));
		txtHora70.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtHora70.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(txtHora70);

		txtHora110 = new JLabel("HORA EXTRA110%");
		txtHora110.setBounds(748, 474, 226, 37);
		txtHora110.setForeground(new Color(50, 205, 50));
		txtHora110.setHorizontalAlignment(SwingConstants.CENTER);
		txtHora110.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.add(txtHora110);
		
		txtHora70_1 = new JLabel("HORA EXTRA70%");
		txtHora70_1.setBounds(506, 439, 226, 37);
		
		txtHora70_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtHora70_1.setForeground(Color.BLACK);
		txtHora70_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.add(txtHora70_1);
		
		txtHora70_2 = new JLabel("HORA EXTRA110%");
		txtHora70_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtHora70_2.setForeground(Color.BLACK);
		txtHora70_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtHora70_2.setBounds(748, 439, 226, 37);
		contentPane.add(txtHora70_2);
		
		textField = new JTextField();
		textField.setText("15/01/2024");
		textField.setBounds(10, 439, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setText("15/02/2024");
		textField_1.setColumns(10);
		textField_1.setBounds(119, 439, 86, 20);
		contentPane.add(textField_1);
		
		btnNewButton = new JButton("Pesquisar por Periodo");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//aqui atualiza a table
			}
		});
		btnNewButton.setBounds(221, 439, 157, 23);
		contentPane.add(btnNewButton);
	}

	

	Duration cem70 = Duration.ZERO;
	Duration cem110 = Duration.ZERO;
	String msg = "";
	boolean hr70 = false;
	boolean hr110 = false;
	private JLabel txtHora70_2;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnNewButton;
	
	
	public void listar(LocalDate dataInicio, LocalDate dataFim) {
		LocalDateTime datJornada;
		Duration duracaoJornada = Duration.ZERO;
		Duration duracaoAlmoco = Duration.ZERO;
		int cont = 0;

		List<Jornada> jornadas = jornadaController.ListaPeriodo(dataInicio.atStartOfDay(), dataFim.atTime(23, 59));
		model.setRowCount(0);
		
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

			model.addRow(new Object[] { cont, utils.converterFormatoData(datJornada),
					utils.formatarData(startJornada), utils.formatarData(endJornada),
					utils.formatarData(startAlmoco), utils.formatarData(endAlmoco),
					utils.formatarDuration(hrExtradiaria), porcentagem });

			
			if (porcentagem == 70) {
				hr70 = true;
				
				
				
					cem70 = cem70.plus(hrExtradiaria);
					txtHora70.setText(utils.formatarDuration(cem70));
					txtHora70_1.setText("HORA 70%");
				
				
			} else {
				txtHora110.setText("N/A Hora Extra");
			}
		
			if (porcentagem == 110) {
				cem110 = cem110.plus(hrExtradiaria); 
				
				
				
				
				
				txtHora110.setText(utils.formatarDuration(cem110));
				hr110= true;
			} else {
				txtHora110.setText("N/A Hora Extra");
			}
			
			
			
		}

		
		model.addRow(new Object[] { "**********************", "**********************", "**********************",
				"**********************", "**********************", "**********************", "**********************",
				"**********************" });
		if(hr70) {
			model.addRow(new Object[] { "------------", "------------", "------------", "------------", "------------",
					"Hora Mensal   70%", utils.formatarDuration(cem70), "------------" });
		}
		
		if(hr110) {
			model.addRow(new Object[] { "------------", "------------", "------------", "------------", "------------",
					"Hora Mensal 110%", utils.formatarDuration(cem110), "------------" });
		}
		

	}
}
