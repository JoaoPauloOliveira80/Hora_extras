package application;

import java.time.LocalDate;

import application.controller.JornadaController;
import application.dao.JornadaDAO;
import application.grafic.JanelaPrincipal;

public class Application {
    public static void main(String[] args) {
        JornadaService jornadaService = new JornadaService(new JornadaDAO());
        JornadaController jornadaController = new JornadaController(jornadaService);

        JanelaPrincipal janelaPrincipal = new JanelaPrincipal(jornadaController);

        LocalDate dataInicio = LocalDate.of(2024, 1, 26);
        LocalDate dataFim = LocalDate.of(2024, 2, 25);

        janelaPrincipal.listar(dataInicio, dataFim);
        janelaPrincipal.setVisible(true);
    }
}
