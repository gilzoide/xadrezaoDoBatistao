/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 30/03/2014
 */
package ui;

import xadrez.Tabuleiro;
import xadrez.peca.Peao;
import xadrez.peca.Rei;

import java.util.ArrayList;

public class Jogador {
	private Cor cor;
	/// Referências de peças importantes (que têm update);
	private ArrayList<Peao> piaums;
	private Rei reizaum;
	
	/**
	 * Ctor
	 */
	public Jogador (Cor nova_cor) {
		cor = nova_cor;
		piaums = new ArrayList<> ();
	}
	/**
	 * Marca as peças importantes (com base na posição de começo de jogo)
	 * 
	 * @note peças importantes = peões + rei
	 */
	public void novoJogo () {
		piaums.clear ();
		// linha dos peões
		int linha = (cor == Cor.BRANCO) ? 6 : 1;
		for (int i = 0; i < 8; i++) {
			piaums.add ((Peao) Tabuleiro.getTabuleiro ().getCasa (linha, i).getPeca ());
		}
		linha = (cor == Cor.BRANCO) ? 7 : 0;
		reizaum = (Rei) Tabuleiro.getTabuleiro ().getCasa (linha, 3).getPeca ();
	}
	
	public void update () {
		
	}
	
	/* GETTERS */
	public Cor getCor () {
		return cor;
	}
}
