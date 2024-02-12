package application.grafic;

import java.awt.Color;
import java.awt.Font;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import application.controller.JornadaController;
import application.model.Centralizar;
import application.model.Jornada;
import application.utils.DateTimeFormatUtils;

public class JanelaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    private DateTimeFormatUtils formatarUtils = new DateTimeFormatUtils();
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JornadaController jornadaController;
    private JLabel txtHora70, txtHora110;
    private JLabel lblNewLabel;
    private JLabel lblTotalHoraExtra;

    @SuppressWarnings("serial")
	public JanelaPrincipal(JornadaController jornadaController) {
        this.jornadaController = jornadaController;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 807, 481);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        model = new DefaultTableModel(new Object[] { "Dia trabalhado", "Data", "Início da Jornada", "Fim da Jornada",
                "Início do Almoço", "Fim do Almoço", "Hora extra dia", "Porcentagem" }, 0) {
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
        scrollPane.setBounds(5, 5, 781, 323);
        contentPane.add(scrollPane);

        txtHora70 = new JLabel("HORA EXTRA70%");
        txtHora70.setForeground(new Color(50, 205, 50));
        txtHora70.setFont(new Font("Tahoma", Font.BOLD, 18));
        txtHora70.setHorizontalAlignment(SwingConstants.CENTER);
        txtHora70.setBounds(112, 405, 226, 37);
        contentPane.add(txtHora70);

        JLabel label2 = new JLabel("Label 2");
        label2.setBounds(5, 460, 100, 20);
        contentPane.add(label2);

        txtHora110 = new JLabel("HORA EXTRA110%");
        txtHora110.setForeground(new Color(50, 205, 50));
        txtHora110.setHorizontalAlignment(SwingConstants.CENTER);
        txtHora110.setFont(new Font("Tahoma", Font.BOLD, 18));
        txtHora110.setBounds(402, 405, 226, 37);
        contentPane.add(txtHora110);

        lblNewLabel = new JLabel("TOTAL HORA EXTRA 70%");
        lblNewLabel.setForeground(new Color(47, 79, 79));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(135, 379, 180, 25);
        contentPane.add(lblNewLabel);

        lblTotalHoraExtra = new JLabel("TOTAL HORA EXTRA 110%");
        lblTotalHoraExtra.setHorizontalAlignment(SwingConstants.CENTER);
        lblTotalHoraExtra.setForeground(new Color(47, 79, 79));
        lblTotalHoraExtra.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTotalHoraExtra.setBounds(419, 379, 180, 25);
        contentPane.add(lblTotalHoraExtra);
    }

    public void listar(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime datJornada;
        Duration hrTotalMes70 = Duration.ZERO;
        Duration totalJornada110 = Duration.ZERO;
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
            int porcentagem;

            datJornada = jornada.getEndJornada();
            startJornada = jornada.getStartJornada();
            endJornada = jornada.getEndJornada();
            startAlmoco = jornada.getStartRefeicao();
            endAlmoco = jornada.getEndRefeicao();
            porcentagem = jornada.getPorcentagem();

            duracaoJornada = Duration.between(startJornada, endJornada);
            duracaoAlmoco = Duration.between(startAlmoco, endAlmoco);

            Duration hrExtradiaria = formatarUtils.calcularTotalHora(duracaoJornada, duracaoAlmoco);

            model.addRow(new Object[] { cont, formatarUtils.converterFormatoData(datJornada),
                    formatarUtils.formatarData(startJornada), formatarUtils.formatarData(endJornada),
                    formatarUtils.formatarData(startAlmoco), formatarUtils.formatarData(endAlmoco),
                    formatarUtils.formatarDuration(hrExtradiaria), porcentagem });

            if (porcentagem == 70) {
                hrTotalMes70 = hrTotalMes70.plus(hrExtradiaria);
                txtHora70.setText(formatarUtils.formatarDuration(hrTotalMes70));
            }

            if (porcentagem == 110) {
                totalJornada110 = totalJornada110.plus(hrExtradiaria);
                txtHora110.setText(formatarUtils.formatarDuration(totalJornada110));
            }
        }

        model.addRow(new Object[] { "------------", "------------", "------------", "------------", "------------",
                "Hora Mensal", formatarUtils.formatarDuration(hrTotalMes70.plus(totalJornada110)), "------------" });
    }

    public void atualizar() {
        // Lógica de atualização da tabela
        // Certifique-se de chamar 'model.fireTableDataChanged()' após fazer as atualizações
    }
}
