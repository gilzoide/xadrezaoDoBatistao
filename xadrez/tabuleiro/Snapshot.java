/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package xadrez.tabuleiro;

import xadrez.movimento.Movimento;
import xadrez.peca.*;
import ui.Gui;
import ui.Cor;
import ui.Jogador;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.Serializable;

/**
 * Snapshot é um snapshot do tabuleiro.
 * 
 * Pode ser usado pra salvar o jogo, pra salvar jogadas,
 * pra voltar no tempo, checar empate, essas coisas
 */
public class Snapshot implements Serializable {
	private byte[] snap;	/// o snapshot em si (em um vetor 8x8=64)
	private Movimento mov;	/// movimento que deu origem a esse snap; null pra início de jogo
	private String log;		/// log naquele momento
	
	/**
	 * Ctor: tira o snapshot do tabuleiro
	 */
	public Snapshot (Movimento mov) {
		snap = new byte[64];
		this.mov = mov;
		log = Gui.getTela ().getLog ();
		
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
		
		ArrayList<Peca> todas = new ArrayList<> ();
		ArrayList<Peca> brancas = new ArrayList<> ();
		ArrayList<Peca> pretas = new ArrayList<> ();
		Peca aux;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// vê a cor do sujeito (racismo =/)
				Cor cor = (snap[cont] % 2 == 0) ? Cor.PRETO : Cor.BRANCO;
				
				switch (snap[cont] & 0b1110) {
					// alguém
					case 2: aux = new Torre (cor, i, j); tab.getCasa (i, j).setPeca (aux); todas.add (aux); break;
					case 4: aux = new Cavalo (cor, i, j); tab.getCasa (i, j).setPeca (aux); todas.add (aux); break;
					case 6: aux = new Bispo (cor, i, j); tab.getCasa (i, j).setPeca (aux); todas.add (aux); break;
					case 8: aux = new Dama (cor, i, j); tab.getCasa (i, j).setPeca (aux); todas.add (aux); break;
					case 10: aux = new Rei (cor, i, j); tab.getCasa (i, j).setPeca (aux); todas.add (aux); break;
					// em especial, um peão
					case 12: aux = new Peao (cor, i, j, snap[cont] >>> 5); tab.getCasa (i, j).setPeca (aux); todas.add (aux); break;
					
					// ninguém
					default: tab.getCasa (i, j).setPeca (null);
				}
				
				// próximo
				cont++;
			}
		}
		tab.refreshTabuleiro ();
		
		for (Peca P : todas) {
			if (P.getCor () == Cor.BRANCO)
				brancas.add (P);
			else
				pretas.add (P);
		}
		branco.setPecas (brancas);
		preto.setPecas (pretas);
	}
	
	/* GETTERS */
	public Movimento getMov () {
		return mov;
	}
	public String getLog () {
		return log;
	}
	public String getTabString () {
		String aux = "";
		for (int i = 0; i < 64; i++) {
			char peca;
			// peças
			switch (snap[i] & 0b1110) {
				case 2: peca = 'T'; break;
				case 4: peca = 'C'; break;
				case 6: peca = 'B'; break;
				case 8: peca = 'D'; break;
				case 10: peca = 'R'; break;
				case 12: peca = 'P'; System.out.print ((snap[i] >>> 5) + " "); break;
				default: peca = ' ';
			}
			// se for preto, fica minúsculo
			if (snap[i] % 2 == 0)
				peca = Character.toLowerCase (peca);
			
			aux += peca;
			// pula linha no fim do tabuleiro
			if (i % 8 == 7)
				aux += '\n';
		}
		return aux;
	}
	
	@Override
	public String toString () {
		return "Snap Hash no: " + Arrays.hashCode (snap) + "\n";
	}
}
