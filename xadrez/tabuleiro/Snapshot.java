/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 20/04/2014
 */
package xadrez.tabuleiro;

import xadrez.movimento.Movimento;
import xadrez.peca.*;
import ui.Cor;
import ui.Jogador;

import java.util.ArrayList;

/**
 * Snapshot é um snapshot do tabuleiro.
 * 
 * Pode ser usado pra salvar o jogo, pra salvar jogadas,
 * pra voltar no tempo, checar empate, essas coisas
 * 
 * É Comparable pra saber quem moveu:
 * 	 + se 0, são iguais;
 * 	 + se >0, BRANCO moveu;
 * 	 + se <0, PRETO moveu.
 */
public class Snapshot implements Comparable<Snapshot> {
	private byte[] snap;	/// o snapshot em si (em um vetor 8x8=64)
	private Movimento mov;	/// movimento que deu origem ao snapshot - null pra snap do início do jogo
	private int last;	/// Último snap antes desse
	
	/**
	 * Ctor: tira o snapshot do tabuleiro
	 */
	public Snapshot (int last, Movimento mov) {
		snap = new byte[64];
		this.mov = mov;
		this.last = last;
		
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		int cont = 0;
		// copia cada casa do tabuleiro
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Casa aux = tab.getCasa (i, j);
				
				if (aux.getPeca () != null)
					snap[cont] = aux.getPeca ().getMask ();
				else
					snap[cont] = 0;
				
				cont++;
			}
		}
	}
	
	/**
	 * Usa um snapshot pra refazer o tabuleiro (usado no 'undo' e 'load')
	 * 
	 * Percorre o snap pra refazer o tabuleiro, a partir das informações
	 * das peças lá dentro. Repõe também a lista de peças dos jogadores.
	 */
	public void povoaTabuleiro (Jogador branco, Jogador preto) {
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		int cont = 0;
		
		ArrayList<Peca> brancas = new ArrayList<> ();
		ArrayList<Peca> pretas = new ArrayList<> ();
		Peca aux;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				switch (snap[cont]) {
					// peças brancas
					case 9: aux = new Peao (Cor.BRANCO, i, j); tab.getCasa (i, j).setPeca (aux); brancas.add (aux); break;
					case 10: aux = new Torre (Cor.BRANCO, i, j); tab.getCasa (i, j).setPeca (aux); brancas.add (aux); break;
					case 11: aux = new Cavalo (Cor.BRANCO, i, j); tab.getCasa (i, j).setPeca (aux); brancas.add (aux); break;
					case 12: aux = new Bispo (Cor.BRANCO, i, j); tab.getCasa (i, j).setPeca (aux); brancas.add (aux); break;
					case 13: aux = new Dama (Cor.BRANCO, i, j); tab.getCasa (i, j).setPeca (aux); brancas.add (aux); break;
					case 14: aux = new Rei (Cor.BRANCO, i, j); tab.getCasa (i, j).setPeca (aux); brancas.add (aux); break;
					
					// peças pretas
					case 1: aux = new Peao (Cor.PRETO, i, j); tab.getCasa (i, j).setPeca (aux); pretas.add (aux); break;
					case 2: aux = new Torre (Cor.PRETO, i, j); tab.getCasa (i, j).setPeca (aux); pretas.add (aux); break;
					case 3: aux = new Cavalo (Cor.PRETO, i, j); tab.getCasa (i, j).setPeca (aux); pretas.add (aux); break;
					case 4: aux = new Bispo (Cor.PRETO, i, j); tab.getCasa (i, j).setPeca (aux); pretas.add (aux); break;
					case 5: aux = new Dama (Cor.PRETO, i, j); tab.getCasa (i, j).setPeca (aux); pretas.add (aux); break;
					case 6: aux = new Rei (Cor.PRETO, i, j); tab.getCasa (i, j).setPeca (aux); pretas.add (aux); break;
					
					// ninguém
					default: tab.getCasa (i, j).setPeca (null);
					//System.out.print (i + "x" + j + " ");
				}
				
				// próximo
				cont++;
			}
		}
		tab.refreshTabuleiro ();
		
		branco.setPecas (brancas);
		preto.setPecas (pretas);
	}

	@Override
	public int compareTo (Snapshot S) {
		int retorno = 0;
		
		// percorre o tabuleiro, achando as diferenças
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				
			}
		}
		
		return retorno;
	}
	
	/* GETTERS */
	public int getLast () {
		return last;
	}
}
