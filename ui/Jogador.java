/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 30/03/2014
 */
package ui;

import xadrez.Tabuleiro;
import xadrez.peca.Peca;
import xadrez.peca.Peao;
import xadrez.peca.Rei;

import java.util.ArrayList;

public class Jogador {
	private Cor cor;
	/// Referências de peças importantes (que têm update);
	private ArrayList<Peca> todas_pecas;
	private ArrayList<Peao> piaums;
	private Rei reizaum;
	
	/**
	 * Ctor
	 */
	public Jogador (Cor nova_cor) {
		cor = nova_cor;
		piaums = new ArrayList<> ();
		todas_pecas = new ArrayList<> ();
	}
	/**
	 * Marca as peças importantes (com base na posição de começo de jogo)
	 * 
	 * @note peças importantes = peões + rei
	 */
	public void novoJogo () {
		todas_pecas.clear ();
		piaums.clear ();
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		
		// linha dos peões
		int linha = (cor == Cor.BRANCO) ? 6 : 1;
		for (int i = 0; i < 8; i++) {
			todas_pecas.add (tab.getCasa (linha, i).getPeca ());
			piaums.add ((Peao) tab.getCasa (linha, i).getPeca ());
		}
		// linha dos não peões
		linha = (cor == Cor.BRANCO) ? 7 : 0;
		for (int i = 0; i < 8; i++) {
			todas_pecas.add (tab.getCasa (linha, i).getPeca ());
		}
		reizaum = (Rei) todas_pecas.get (11);
	}
	
	/**
	 * Update por jogada: checa xeque
	 */
	public void update () {
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tab.getCasa (i, j).removeDominio (cor);
			}
		}
		
		for (Peca P : todas_pecas) {
			if (!P.estaMorto ())
				P.domina ();
		}
		
		if (Tabuleiro.getTabuleiro ().getCasa (reizaum.getCoord ()).getDominio ().ameaca (cor)) {
			Gui.getTela ().xeque (this);
		}

		//tab.printDominio ();
	}
	/**
	 * Dá um update nos peões - só pra quem acabou de jogar
	 */
	public void updatePiaums () {
		for (Peao P : piaums)
			P.update (false);
	}
	
	/* GETTERS */
	public Cor getCor () {
		return cor;
	}
	
	
	public String toString () {
		return "Jogador " + cor;
	}
}
