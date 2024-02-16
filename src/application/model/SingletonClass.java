package application.model;

public class SingletonClass {
    //variavel que guardar� a instancia a ser retornada qunado j� existir uma
	private static SingletonClass instance = null;

    //define o construtor como private assim n�o se� possivel instanciar a classe externamente
	private SingletonClass() {
	}

    //m�todo que pode ser utilizado para �instanciar� a classe
	public static SingletonClass getInstance() {
		if (instance == null)
			instance = new SingletonClass();

		return instance;
	}
}