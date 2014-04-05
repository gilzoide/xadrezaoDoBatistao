/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 30/03/2014
 */
package ui;

import xadrez.Casa;
import xadrez.Tabuleiro;
import xadrez.Movimento;
import xadrez.peca.Peca;
import xadrez.peca.Peao;
import xadrez.peca.Rei;

import java.awt.Point;
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
			if (i != 4)		// pula o rei
				todas_pecas.add (tab.getCasa (linha, i).getPeca ());
		}
		// e o reizão xD
		reizaum = (Rei) tab.getCasa (linha, 4).getPeca ();
		todas_pecas.add (reizaum);
	}
	
	/**
	 * Update por jogada: checa xeque
	 */
	public void update (Jogador outro_jogador) {
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				tab.getCasa (i, j).removeDominio (cor);
		}
		
		// recalcula movimentos possíveis e domina o campo
		movs.clear ();
		for (Peca P : todas_pecas) {
			if (!P.estaMorto ()) {
				ArrayList<Movimento> aux = P.possiveisMovimentos ();
								
				// se não tem movimento possível, indice_comeco e indice_fim serão iguais
				P.setIndiceComeco (movs.size ());
				movs.addAll (aux);
				P.setIndiceFim (movs.size ());
			}
		}
		
		if (movs.isEmpty ())
			Gui.getTela ().mate (this);
	}
	
	/**
	 * Simula a jogada (pondo os domínio no simulador)
	 */
	public void simulaDominios (Casa[][] simulador) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				simulador[i][j].removeDominio (cor);
		}
		for (Peca P : todas_pecas) {
			if (!P.estaMorto ())
				P.domina (simulador);
		}
	}
	
	public void updateRei () {
		ArrayList<Movimento> aux = reizaum.possiveisMovimentos ();
		// se não tem movimento possível, indice_comeco e indice_fim serão iguais
		reizaum.setIndiceComeco (movs.size ());
		movs.addAll (aux);
		reizaum.setIndiceFim (movs.size ());
	}
	
	/**
	 * Rei deste jogador está em xeque?
	 */
	public boolean estaXeque () {
		return Tabuleiro.getTabuleiro ().getCasa (reizaum.getCoord ()).getDominio ().ameaca (cor);
	}
	public boolean estaXeque (Casa[][] simulador) {
		return simulador [(int) reizaum.getCoord ().getY ()][(int) reizaum.getCoord ().getX ()].getDominio ().ameaca (cor);
	}
	/**
	 * Dá um update nos peões - só pra quem acabou de jogar
	 */
	public void updatePiaums () {
		for (Peao P : piaums)
			if (!P.estaMorto ())
				P.update (false);
	}
	
	/* GETTERS */
	public Cor getCor () {
		return cor;
	}
	public List<Movimento> getMovs (Peca P) {
		return movs.subList (P.getIndiceComeco (), P.getIndiceFim ());
	}
	public Point getReizaum () {
		return reizaum.getCoord ();
	}
	
	
	public String toString () {
		return "Jogador " + cor;
	}
}
