package br.com.walkito.app;

import org.controlsfx.control.Notifications;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

public class TelaSelecao extends GridPane {
	
	private Label titulo = new Label("Campo Minado");	
	private Label qtdCasas = new Label("Quantos por Quantos?");
	private TextField numero = new TextField();
	private HBox boxNumero = new HBox();
	private HBox boxBotao = new HBox();
	private Button iniciar = new Button("Começar!");
	
	public TelaSelecao() {
		getColumnConstraints().addAll(colunas(),colunas(),colunas(),colunas(),colunas());
		getRowConstraints().addAll(linhas(),linhas(),linhas(),linhas(),linhas());		
		getStyleClass().add("tela");
		
		componentesTela();
		acaoBotao();
	}
	
	private void componentesTela() {
		titulo.getStyleClass().add("titulo");
		add(titulo, 1, 0, 4, 1);
		
		qtdCasas.getStyleClass().add("qtdLabel");
		add(qtdCasas, 1, 2, 4, 1);

		numero.setMaxWidth(30);
		boxNumero.getChildren().add(numero);
		boxNumero.setAlignment(Pos.CENTER);
		add(boxNumero, 2, 3);
		
		iniciar.getStyleClass().add("botao");
		boxBotao.getChildren().add(iniciar);
		boxBotao.setAlignment(Pos.CENTER);
		add(boxBotao, 3, 4, 2, 1);
	}
	
	private void acaoBotao() {
		iniciar.setOnAction(e -> {
			if (numero.getText().equals("") || numero.getText().equals(null)) {
				Notifications.create()
				.position(Pos.CENTER)
				.title("Erro!")
				.text("Campo de número vazio. Por favor, adicione um número.")
				.showError();
			} else if (Integer.parseInt(numero.getText()) <= 0) {
				Notifications.create()
				.position(Pos.CENTER)
				.title("Erro!")
				.text("Campo de número inválido. Por favor, adicione um número válido.")
				.showError();				
			} else if (Integer.parseInt(numero.getText()) <= 2) {
				Notifications.create()
				.position(Pos.CENTER)
				.title("Erro!")
				.text("O número mínimo para o campo minado é de 3x3.")
				.showError();
			} else {
				int numeroCasas = Integer.parseInt(numero.getText());
				int tamanho = 0;
				int tamanhoCampo = 0;
								
				if (numeroCasas < 14) {
					tamanhoCampo = 50;
					tamanho = tamanhoCampo * numeroCasas;
				} else if (numeroCasas >= 14 && numeroCasas <= 29) {
					tamanhoCampo = 25;
					tamanho = tamanhoCampo * numeroCasas;				
				} else if (numeroCasas >= 30) {
					tamanhoCampo = 20;
					tamanho = tamanhoCampo * numeroCasas;
				}
				
				String caminhoCSS = getClass().getResource("/br/com/walkito/app/TelaJogo.css").toExternalForm();
				Scene telaJogo = new Scene(new TelaJogo(numeroCasas, tamanhoCampo), tamanho,tamanho);
				telaJogo.getStylesheets().add(caminhoCSS);
				
				Launcher.janela.setScene(telaJogo);
				Launcher.janela.setResizable(false);
				Launcher.janela.setMaxWidth(1366);
				Launcher.janela.setMaxHeight(768);
				Launcher.janela.centerOnScreen();
				Launcher.janela.setTitle("Campo Minado");
			}
		});
	}
	
	private ColumnConstraints colunas() {
		ColumnConstraints coluna = new ColumnConstraints();
		coluna.setPercentWidth(20);
		coluna.setFillWidth(true);
		return coluna;
	}
	
	private RowConstraints linhas() {
		RowConstraints linha = new RowConstraints();
		linha.setPercentHeight(20);
		linha.setFillHeight(true);
		return linha;
	}
}
