package application.model;

public class SingletonClass {
    //variavel que guardará a instancia a ser retornada qunado já existir uma
	private static SingletonClass instance = null;

    //define o construtor como private assim não seá possivel instanciar a classe externamente
	private SingletonClass() {
	}

    //método que pode ser utilizado para “instanciar” a classe
	public static SingletonClass getInstance() {
		if (instance == null)
			instance = new SingletonClass();

		return instance;
	}
}