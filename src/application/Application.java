package application;

import java.time.LocalDate;

import application.controller.JornadaController;
import application.dao.JornadaDAO;
import application.grafic.JanelaPrincipal;
import application.service.JornadaService;

public class Application {
	public static void main(String[] args) {
		JanelaPrincipal instance;

		JornadaService jornadaService = new JornadaService(new JornadaDAO());
		JornadaController jornadaController = new JornadaController(jornadaService);

		LocalDate today = LocalDate.now();
		LocalDate dataInicio = LocalDate.of(today.minusMonths(1).getYear(), today.minusMonths(1).getMonth(), 26);
		LocalDate dataFim = LocalDate.of(today.getYear(), today.getMonth(), 25);

		JanelaPrincipal janelaPrincipal = new JanelaPrincipal(jornadaController, dataInicio, dataFim);
		janelaPrincipal.listar(dataInicio, dataFim);

		janelaPrincipal.setVisible(true);

		//jornadaController.listAll();
	}
}
