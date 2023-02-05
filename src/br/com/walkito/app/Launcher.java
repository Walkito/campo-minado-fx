package br.com.walkito.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application{
	private String caminhoCSS = getClass().getResource("/br/com/walkito/app/TelaSelecao.css").toExternalForm();
	static Stage janela;
	static Scene cenaPrincipal = new Scene(new TelaSelecao(), 300,250);
	@Override
	public void start(Stage primaryStage) throws Exception {
		cenaPrincipal.getStylesheets().add(caminhoCSS);
		
		janela = primaryStage;
		janela.setScene(cenaPrincipal);
		janela.setTitle("Campo Minado");
		janela.setResizable(false);
		janela.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
