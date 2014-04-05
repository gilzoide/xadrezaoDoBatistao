/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 30/03/2014
 */
package ui;

import xadrez.Tabuleiro;
import xadrez.Movimento;
import xadrez.peca.Peca;
import xadrez.peca.Peao;
import xadrez.peca.Rei;

import java.util.ArrayList;
import java.util.List;
import java.lang.NullPointerException;

public class Jogador {
	private Cor cor;
	/// Referências de peças importantes (que têm update);
	private ArrayList<Peca> todas_pecas;
	private ArrayList<Peao> piaums;
	private Rei reizaum;
	
	/// Todos os movimentos possíveis naquela rodada!
	private ArrayList<Movimento> movs;
	/**
	 * Ctor
	 */
	public Jogador (Cor nova_cor) {
		cor = nova_cor;
		piaums = new ArrayList<> ();
		todas_pecas = new ArrayList<> ();
		movs = new ArrayList<> ();
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
		reizaum = (Rei) todas_pecas.get (12);
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
		
		// recalcula movimentos possíveis e domina o campo
		movs.clear ();
		for (Peca P : todas_pecas) {
			if (!P.estaMorto ()) {
				P.domina ();
				ArrayList<Movimento> aux = P.possiveisMovimentos ();
				// se não tem movimento possível, indice_comeco e indice_fim serão iguais
				P.setIndiceComeco (movs.size ());
				movs.addAll (aux);
				P.setIndiceFim (movs.size ());
			}
		}
		
		if (estaXeque ()) {
			Gui.getTela ().xeque (this);
		}

		//tab.printDominio ();
	}
	/**
	 * Rei deste jogador está em xeque?
	 */
	public boolean estaXeque () {
		return Tabuleiro.getTabuleiro ().getCasa (reizaum.getCoord ()).getDominio ().ameaca (cor);
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
	public List<Movimento> getMovs (Peca P) {
		return movs.subList (P.getIndiceComeco (), P.getIndiceFim ());
	}
	
	
	public String toString () {
		return "Jogador " + cor;
	}
}
