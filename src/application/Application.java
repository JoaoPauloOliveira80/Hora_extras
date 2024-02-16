package application;

import java.time.LocalDate;

import application.controller.JornadaController;
import application.dao.JornadaDAO;
import application.grafic.JanelaPrincipal;

public class Application {
	public static void main(String[] args) {
		
		
	
		JornadaService jornadaService = new JornadaService(new JornadaDAO());
		JornadaController jornadaController = new JornadaController(jornadaService);

		

		
		
		LocalDate dataInicio = LocalDate.of(2023, 12, 26);
        LocalDate dataFim = LocalDate.of(2024, 1, 25);

        JanelaPrincipal janelaPrincipal = new JanelaPrincipal(jornadaController, dataInicio, dataFim);

		
//		LocalDate dataInicio = LocalDate.of(2023, 11, 26);
//		LocalDate dataFim = LocalDate.of(2023, 12, 25);
		
		
		

		janelaPrincipal.listar(dataInicio, dataFim);
		janelaPrincipal.setVisible(true);
		
		jornadaController.listAll();
	}
}
