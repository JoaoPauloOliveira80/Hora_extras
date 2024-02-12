package application.model;

import java.time.LocalDateTime;
import java.time.Duration;

public class IntervaloAlmoco {
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public IntervaloAlmoco(LocalDateTime inicio, LocalDateTime fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public Duration calcularDuracao() {
        return Duration.between(inicio, fim);
    }

	public LocalDateTime getInicio() {
		return inicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.inicio = inicio;
	}

	public LocalDateTime getFim() {
		return fim;
	}

	public void setFim(LocalDateTime fim) {
		this.fim = fim;
	}

	
}