package br.com.walkito.app;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.controlsfx.control.Notifications;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class TelaJogo extends GridPane {
	static List<Button> campos = new ArrayList<>();
	static Dictionary<String, String> camposMarcados = new Hashtable<>();
	boolean voltar = false;
	String statusJogo = "Jogando";

	public TelaJogo(int numeroCasas, int tamanho) {
		resetarJogo();
		for (int i = 0; i < numeroCasas; i++) {
			getColumnConstraints().add(colunas(tamanho));
			getRowConstraints().add(linhas(tamanho));
		}
		
		TelaJogoController jogoController = new TelaJogoController(numeroCasas);
		jogoController.sortearMinas(numeroCasas);
		
		montarCampos(numeroCasas, tamanho);
		acoesCampos();
		setGridLinesVisible(true);
	}
	
	private void resetarJogo() {
		int tamanhoCampos = campos.size();
		int tamanhoCamposMarcados = camposMarcados.size();
		int tamanhoMinas = TelaJogoController.numeroMinas;
		
		for(int j = 0; j < tamanhoCampos; j++) {
			getChildren().remove(campos.get(0));
			campos.remove(0);
		}
		
		for(int i = 0; i < tamanhoCamposMarcados; i++) {
			camposMarcados.remove(0);
		}
		
		for(int x = 0; x < tamanhoMinas; x++) {
			TelaJogoController.minas.remove(0);
		}
				
		statusJogo = "Jogando";
		TelaJogoController.camposAbertos = 0;
		TelaJogoController.numeroMinas = 0;
		TelaJogoController.condicaoVitoria = 0;
	}
	
	private void acoesCampos() {
		TelaJogoController jogoController = new TelaJogoController();
		
		for (int i = 0; i < campos.size(); i++) {
				int indice = i;
				campos.get(indice).setOnMouseClicked(e ->{
					if (statusJogo.equals("Derrota")) {
						Button btnVoltar = new Button("Voltar");
						btnVoltar.getStyleClass().add("botao-voltar");
						add(btnVoltar, 0,0,2,1);
							btnVoltar.setOnAction(r ->{
								Launcher.janela.setScene(Launcher.cenaPrincipal);
								Launcher.janela.setResizable(false);
								Launcher.janela.centerOnScreen();
								Launcher.janela.setTitle("Campo Minado");
							});		
	
					} else {
						String textoCampo = campos.get(indice).getText();
						if(e.getButton() == MouseButton.PRIMARY) {
							if (!textoCampo.equals("?") && !textoCampo.equals("Aberto") && !textoCampo.equals("1") && !textoCampo.equals("2") && !textoCampo.equals("3")) {
								if (textoCampo.equals("Mina")){
									for (int j = 0; j < campos.size(); j++) {
										String textoCampo2 = campos.get(j).getText();
										if (textoCampo2.equals("Mina")) {
											campos.get(j).getStyleClass().add("campos-mina");
	
										}
									}
									Notifications.create()
									.position(Pos.CENTER)
									.title("Game Over!")
									.text("GAME OVER!")
									.showError();
									statusJogo = "Derrota";
								} else {
									campos.get(indice).getStyleClass().add("campos-normal");
									campos.get(indice).setText("Aberto");
									TelaJogoController.camposAbertos = TelaJogoController.camposAbertos + 1;
									if (jogoController.verificarCamposMinasDist1(textoCampo, TelaJogo.campos.get(indice))) {
									} else if (jogoController.verificarCamposMinasDist2(textoCampo, TelaJogo.campos.get(indice))) {
									} else if (jogoController.verificarCamposMinasDist3(textoCampo, TelaJogo.campos.get(indice))){
									} else {jogoController.expandirCampos(textoCampo);}
								}
							}
							if (TelaJogoController.camposAbertos == TelaJogoController.condicaoVitoria) {
								Notifications.create()
								.position(Pos.CENTER)
								.title("Vitória")
								.text("Parabéns! Você venceu!")
								.showConfirm();
								statusJogo = "Vitória";
								
								Button btnVoltar = new Button("Voltar");
								btnVoltar.getStyleClass().add("botao-voltar");
								add(btnVoltar, 0,0,2,1);
									btnVoltar.setOnAction(r ->{
										Launcher.janela.setScene(Launcher.cenaPrincipal);
										Launcher.janela.setResizable(false);
										Launcher.janela.centerOnScreen();
										Launcher.janela.setTitle("Campo Minado");
									});		
							}
						} else if(e.getButton() == MouseButton.SECONDARY) {
							if (campos.get(indice).getText().equals("?")) {
								String posicao = camposMarcados.get(campos.get(indice).getId());
								camposMarcados.remove(campos.get(indice).getId());
								campos.get(indice).setText(posicao);
								
								for (int j = 0; j < campos.get(indice).getStyleClass().size(); j++) {
									int ultimoIndice = campos.get(indice).getStyleClass().size() -1;
									campos.get(indice).getStyleClass().remove(ultimoIndice);
								}					
								campos.get(indice).getStyleClass().add("campos-fechado");
							} else {
								camposMarcados.put(campos.get(indice).getId(), campos.get(indice).getText());
								campos.get(indice).setText("?");
								campos.get(indice).getStyleClass().add("campos-marcados");
							}
						}
					}
				});
			}
	}
			
	private void montarCampos(int numeroCampos, int tamanho){
		int k = 0;
		String linhaColuna = "";
		
		for (int i = 0; i < numeroCampos*numeroCampos; i++) {
			campos.add(new Button());
		}	
		
		for (int i = 0; i < numeroCampos; i++) {
			for (int j = 0; j < numeroCampos; j++) {
					campos.get(k).setMaxWidth(tamanho);
					campos.get(k).setMinWidth(tamanho);
					campos.get(k).setMaxHeight(tamanho);
					campos.get(k).setMinHeight(tamanho);
					campos.get(k).getStyleClass().add("campos-fechado");
					campos.get(k).setId(String.valueOf(k));
					linhaColuna = String.valueOf(i)+":"+String.valueOf(j);
					
					if (TelaJogoController.minas.contains(linhaColuna)) {
						campos.get(k).setText("Mina");
					} else {
						
						campos.get(k).setText(linhaColuna);
					}
					
					this.add(campos.get(k), i, j);
					k++;
				}
			}
		}
	
	private ColumnConstraints colunas(int tamanho) {
		ColumnConstraints coluna = new ColumnConstraints();
		coluna.setMinWidth(tamanho);
		coluna.setMaxWidth(tamanho);
		coluna.setFillWidth(true);
		return coluna;
	}
	
	private RowConstraints linhas(int tamanho) {
		RowConstraints linha = new RowConstraints();
		linha.setMinHeight(tamanho);
		linha.setMaxHeight(tamanho);
		linha.setFillHeight(true);
		return linha;
	}
}
