package application.controller;

import java.time.LocalDateTime;
import java.util.List;

import application.model.Jornada;
import application.service.JornadaService;

public class JornadaController {

	private JornadaService jornadaService;

	public JornadaController() {
		// TODO Auto-generated constructor stub
	}

	public JornadaController(JornadaService jornadaService) {
		this.jornadaService = jornadaService;
	}

	public void getListaPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
		jornadaService.getListaPeriodo(dataInicio, dataFim);
	}

	public List<Jornada> ListaPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
		return jornadaService.listarPorPeriodo(dataInicio, dataFim);
	}

	public List<Jornada> listAll(){
		return jornadaService.listAll();
	}
}
