package br.com.walkito.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.control.Button;

public class TelaJogoController {
	static int numeroMinas;
	static private int totalCasas;
	static private int numeroCasas;
	static int camposAbertos = 0;
	static int condicaoVitoria = 0;
	
	static List<String> minas = new ArrayList<>();
	
	public TelaJogoController() {
	}
	
	public TelaJogoController(int numeroCasas) {
		TelaJogoController.numeroCasas = numeroCasas;
		totalCasas = numeroCasas*numeroCasas;
	}
	
	void expandirCampos(String colunaLinha) {
		String [] colunaLinhaSeparada;
				
		colunaLinhaSeparada = separarColunaLinha(colunaLinha);
		String colunaAtual = colunaLinhaSeparada[0];
		String linhaAtual = colunaLinhaSeparada[1];
	
		String noroeste = String.valueOf((Integer.parseInt(colunaAtual)-1) + ":" + (Integer.parseInt(linhaAtual)-1));
		String norte = colunaAtual + ":" + String.valueOf((Integer.parseInt(linhaAtual)-1));
		String nordeste = String.valueOf((Integer.parseInt(colunaAtual)+1) + ":" + (Integer.parseInt(linhaAtual)-1));
		String leste = String.valueOf((Integer.parseInt(colunaAtual)+1) + ":" + linhaAtual);
		String sudeste = String.valueOf((Integer.parseInt(colunaAtual)+1) + ":" + (Integer.parseInt(linhaAtual)+1));
		String sul = colunaAtual + ":" + String.valueOf((Integer.parseInt(linhaAtual)+1));
		String sudoeste = String.valueOf((Integer.parseInt(colunaAtual)-1) + ":" + (Integer.parseInt(linhaAtual)+1));
		String oeste = String.valueOf((Integer.parseInt(colunaAtual)-1) + ":" + linhaAtual);
		
		abrirCampos(noroeste);
		abrirCampos(norte);
		abrirCampos(nordeste);
		abrirCampos(leste);
		abrirCampos(sudeste);
		abrirCampos(sul);
		abrirCampos(sudoeste);
		abrirCampos(oeste);	
	}
	
	boolean abrirCampos(String direcao) {
		if (!verificarLimite(direcao)) {
			if (!achouMina(direcao)) {
				for (int i = 0; i < TelaJogo.campos.size(); i++) {
					String posCampo = TelaJogo.campos.get(i).getText();
					if (posCampo.equals(direcao)){
						TelaJogo.campos.get(i).getStyleClass().add("campos-normal");
						TelaJogo.campos.get(i).setText("Aberto"+posCampo);
						camposAbertos+=1;
						if (verificarCamposMinasDist1(direcao, TelaJogo.campos.get(i))) {
							return true;
						} else if (verificarCamposMinasDist2(direcao, TelaJogo.campos.get(i))) {
							return true;
						} else if (verificarCamposMinasDist3(direcao, TelaJogo.campos.get(i))){
							return true;
						} else {							
							expandirCampos(posCampo);
						}
					} 
				}
			}
		}
		return false;
	}

	boolean verificarCamposMinasDist3(String posMina, Button campo) {
		String [] colunaLinhaSeparada = separarColunaLinha(posMina);
		List<String> direcoes = new ArrayList<>();
		
		String colunaAtual = colunaLinhaSeparada[0];
		String linhaAtual = colunaLinhaSeparada[1];
		
		String noroeste = String.valueOf((Integer.parseInt(colunaAtual)-3) + ":" + (Integer.parseInt(linhaAtual)-3));
		String norte = colunaAtual + ":" + String.valueOf((Integer.parseInt(linhaAtual)-3));
		String nordeste = String.valueOf((Integer.parseInt(colunaAtual)+3) + ":" + (Integer.parseInt(linhaAtual)-3));
		String leste = String.valueOf((Integer.parseInt(colunaAtual)+3) + ":" + linhaAtual);
		String sudeste = String.valueOf((Integer.parseInt(colunaAtual)+3) + ":" + (Integer.parseInt(linhaAtual)+3));
		String sul = colunaAtual + ":" + String.valueOf((Integer.parseInt(linhaAtual)+3));
		String sudoeste = String.valueOf((Integer.parseInt(colunaAtual)-3) + ":" + (Integer.parseInt(linhaAtual)+3));
		String oeste = String.valueOf((Integer.parseInt(colunaAtual)-3) + ":" + linhaAtual);
		
		if (!limiteMina(noroeste)) { direcoes.add("Noroeste");}
		if (!limiteMina(norte)) { direcoes.add("Norte");}
		if (!limiteMina(nordeste)) { direcoes.add("Nordeste");}
		if (!limiteMina(leste)) { direcoes.add("Leste");}
		if (!limiteMina(sudeste)) { direcoes.add("Sudeste");}
		if (!limiteMina(sul)) { direcoes.add("Sul");}
		if (!limiteMina(sudoeste)) { direcoes.add("Sudoeste");}
		if (!limiteMina(oeste)) { direcoes.add("Oeste");}
		
		if (direcoes.size() == 0) {
			return false;
		}
		
		for (String d: direcoes) {
			switch (d) {
			case "Noroeste":
				adicionaTexto(noroeste, campo, 3);
				break;
			case "Norte":
				adicionaTexto(norte, campo, 3);
				break;
			case "Nordeste":
				adicionaTexto(nordeste, campo, 3);
				break;
			case "Leste":
				adicionaTexto(leste, campo, 3);
				break;
			case "Sudeste":
				adicionaTexto(leste, campo, 3);
				break;
			case "Sul":
				adicionaTexto(sul, campo, 3);
				break;
			case "Sudoeste":
				adicionaTexto(sudoeste, campo, 3);
				break;
			case "Oeste":
				adicionaTexto(oeste, campo, 3);
				break;
			}
		}
		
		return true;
	}	
	
	boolean verificarCamposMinasDist2(String posMina, Button campo) {
		String [] colunaLinhaSeparada = separarColunaLinha(posMina);
		List<String> direcoes = new ArrayList<>();
		
		String colunaAtual = colunaLinhaSeparada[0];
		String linhaAtual = colunaLinhaSeparada[1];
		
		String noroeste = String.valueOf((Integer.parseInt(colunaAtual)-2) + ":" + (Integer.parseInt(linhaAtual)-2));
		String norte = colunaAtual + ":" + String.valueOf((Integer.parseInt(linhaAtual)-2));
		String nordeste = String.valueOf((Integer.parseInt(colunaAtual)+2) + ":" + (Integer.parseInt(linhaAtual)-2));
		String leste = String.valueOf((Integer.parseInt(colunaAtual)+2) + ":" + linhaAtual);
		String sudeste = String.valueOf((Integer.parseInt(colunaAtual)+2) + ":" + (Integer.parseInt(linhaAtual)+2));
		String sul = colunaAtual + ":" + String.valueOf((Integer.parseInt(linhaAtual)+2));
		String sudoeste = String.valueOf((Integer.parseInt(colunaAtual)-2) + ":" + (Integer.parseInt(linhaAtual)+2));
		String oeste = String.valueOf((Integer.parseInt(colunaAtual)-2) + ":" + linhaAtual);
		
		if (!limiteMina(noroeste)) { direcoes.add("Noroeste");}
		if (!limiteMina(norte)) { direcoes.add("Norte");}
		if (!limiteMina(nordeste)) { direcoes.add("Nordeste");}
		if (!limiteMina(leste)) { direcoes.add("Leste");}
		if (!limiteMina(sudeste)) { direcoes.add("Sudeste");}
		if (!limiteMina(sul)) { direcoes.add("Sul");}
		if (!limiteMina(sudoeste)) { direcoes.add("Sudoeste");}
		if (!limiteMina(oeste)) { direcoes.add("Oeste");}
		
		if (direcoes.size() == 0) {
			return false;
		}
		
		for (String d: direcoes) {
			switch (d) {
			case "Noroeste":
				adicionaTexto(noroeste, campo, 2);
				break;
			case "Norte":
				adicionaTexto(norte, campo, 2);
				break;
			case "Nordeste":
				adicionaTexto(nordeste, campo, 2);
				break;
			case "Leste":
				adicionaTexto(leste, campo, 2);
				break;
			case "Sudeste":
				adicionaTexto(leste, campo, 2);
				break;
			case "Sul":
				adicionaTexto(sul, campo, 2);
				break;
			case "Sudoeste":
				adicionaTexto(sudoeste, campo, 2);
				break;
			case "Oeste":
				adicionaTexto(oeste, campo, 2);
				break;
			}
		}
		
		return true;
	}
	
	boolean verificarCamposMinasDist1(String posMina, Button campo) {
		String [] colunaLinhaSeparada = separarColunaLinha(posMina);
		List<String> direcoes = new ArrayList<>();
		
		String colunaAtual = colunaLinhaSeparada[0];
		String linhaAtual = colunaLinhaSeparada[1];
		
		String noroeste = String.valueOf((Integer.parseInt(colunaAtual)-1) + ":" + (Integer.parseInt(linhaAtual)-1));
		String norte = colunaAtual + ":" + String.valueOf((Integer.parseInt(linhaAtual)-1));
		String nordeste = String.valueOf((Integer.parseInt(colunaAtual)+1) + ":" + (Integer.parseInt(linhaAtual)-1));
		String leste = String.valueOf((Integer.parseInt(colunaAtual)+1) + ":" + linhaAtual);
		String sudeste = String.valueOf((Integer.parseInt(colunaAtual)+1) + ":" + (Integer.parseInt(linhaAtual)+1));
		String sul = colunaAtual + ":" + String.valueOf((Integer.parseInt(linhaAtual)+1));
		String sudoeste = String.valueOf((Integer.parseInt(colunaAtual)-1) + ":" + (Integer.parseInt(linhaAtual)+1));
		String oeste = String.valueOf((Integer.parseInt(colunaAtual)-1) + ":" + linhaAtual);
		
		if (!limiteMina(noroeste)) { direcoes.add("Noroeste");}
		if (!limiteMina(norte)) { direcoes.add("Norte");}
		if (!limiteMina(nordeste)) { direcoes.add("Nordeste");}
		if (!limiteMina(leste)) { direcoes.add("Leste");}
		if (!limiteMina(sudeste)) { direcoes.add("Sudeste");}
		if (!limiteMina(sul)) { direcoes.add("Sul");}
		if (!limiteMina(sudoeste)) { direcoes.add("Sudoeste");}
		if (!limiteMina(oeste)) { direcoes.add("Oeste");}
		
		if (direcoes.size() == 0) {
			return false;
		}
		
		for (String d: direcoes) {
			switch (d) {
			case "Noroeste":
				adicionaTexto(noroeste, campo, 1);
				break;
			case "Norte":
				adicionaTexto(norte, campo,1 );
				break;
			case "Nordeste":
				adicionaTexto(nordeste, campo, 1);
				break;
			case "Leste":
				adicionaTexto(leste, campo, 1);
				break;
			case "Sudeste":
				adicionaTexto(leste, campo, 1);
				break;
			case "Sul":
				adicionaTexto(sul, campo, 1);
				break;
			case "Sudoeste":
				adicionaTexto(sudoeste, campo, 1);
				break;
			case "Oeste":
				adicionaTexto(oeste, campo, 1);
				break;
			}
		}
		
		return true;
	}
	
	void adicionaTexto(String posicao, Button campo, int distancia) {
		switch (distancia) {
			case 1:
			campo.setText("1");
			campo.getStyleClass().add("campos-normal-txt1");
			break;
			case 2:
			campo.setText("2");
			campo.getStyleClass().add("campos-normal-txt2");
			break;
			case 3:
			campo.setText("3");
			campo.getStyleClass().add("campos-normal-txt3");			
			break;
		}
	}
		
	String[] separarColunaLinha(String colunaLinha) {
		String [] colunaLinhaSeparada = colunaLinha.split(":");		
		return colunaLinhaSeparada;
	}
	
	boolean limiteMina(String colunaLinha) {
		String [] colunaLinhaSeparada = separarColunaLinha(colunaLinha);
		int coluna = Integer.valueOf(colunaLinhaSeparada[0]);
		int linha = Integer.valueOf(colunaLinhaSeparada[1]);
		
		if (coluna < 0 || coluna > numeroCasas || linha < 0 || linha > numeroCasas){
			return true;
		}
		if (minas.contains(colunaLinha)) {
			return false;
		}
		
		return true;
	}
	
	boolean achouMina(String colunaLinha) {	
		if (minas.contains(colunaLinha)) {
			return true;
		} else {
			return false;			
		}
	}
	
	boolean verificarLimite(String colunaLinha) {
		String [] colunaLinhaSeparada = separarColunaLinha(colunaLinha);
		int coluna = Integer.valueOf(colunaLinhaSeparada[0]);
		int linha = Integer.valueOf(colunaLinhaSeparada[1]);
		
		if (coluna < 0 || coluna > numeroCasas || linha < 0 || linha > numeroCasas){
			return true;
		} else {
			return false;			
		}
	}
	
	void sortearMinas(int numeroCasas) {
		int numAleatorio = 0;
		int coluna = 0;
		int linha = 0;
		String colunaLinha = "";
		
		numeroMinas = totalCasas/9;
		condicaoVitoria = totalCasas - numeroMinas;
		
		for (int i = 0; i < numeroMinas; i++) {
			do {
				Random random = new Random();
				numAleatorio = random.nextInt(numeroCasas);
				coluna = numAleatorio;
				numAleatorio = random.nextInt(numeroCasas);
				linha = numAleatorio;
				colunaLinha = String.valueOf(coluna) + ":" + String.valueOf(linha);					
			} while (minas.contains(colunaLinha));
			
			minas.add(colunaLinha);
		}
			
	}
}
