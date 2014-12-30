/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package xadrez.tabuleiro;

import xadrez.movimento.Movimento;
import xadrez.peca.Peca;
import xadrez.peca.Rei;
import ui.Cor;

import java.awt.Point;

/**
 * Simulador é um simulador do tabuleiro
 * 
 * Serve pra testarmos à vontade os domínios
 * e posições das peças, fazendo-se uma "cópia"
 * do tabuleiro e mexendo nessa, sem bagunçar
 * o jogo em si! Esses testes são úteis pra
 * determinar se movimento é permitido, no caso
 * de não deixar o próprio rei em xeque ;]
 */
public class Simulador {
	private Casa casa[][];
	private Casa donde, pronde;
	private Movimento mov;	/// Movimento a ser simulado
	private Point rei_branco, rei_preto;	/// Posição dos Reizão: pq se é ele que move, muda sua posição
	
	public Simulador (Casa donde, Casa pronde) {
		casa = new Casa[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				casa[i][j] = new Casa (i, j);
		}
		
		// duplica o tabuleiro
		copiaTabuleiro ();
		
		// Casas do simulador ao invés das reais, do tabuleiro
		this.donde = casa[(int) donde.getCoord ().getY ()][(int) donde.getCoord ().getX ()];
		this.pronde = casa[(int) pronde.getCoord ().getY ()][(int) pronde.getCoord ().getX ()];
		// cria o movimento nas casas simuladas
		mov = new Movimento (this.donde, this.pronde);
		mov.mover ();
		
		domina ();
	}
	
	/**
	 * Primeiro precisamos copiar o tabuleiro pra podermos brincar direito
	 */
	private void copiaTabuleiro () {
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				Casa.copia (casa[i][j], tab.getCasa (i, j));
		}
	}
	
	/**
	 * Peças duplicadas dominam o simulador!
	 */
	private void domina () {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Peca aux = casa[i][j].getPeca ();
				if (aux != null) {
					aux.domina (this);
					// se foi o rei, salva lá nos Point
					if (aux instanceof Rei) {
						if (aux.getCor () == Cor.BRANCO)
							rei_branco = new Point (aux.getCoord ());
						else
							rei_preto = new Point (aux.getCoord ());
					}
				}
			}
		}
	}
	
	/* GETTERS */
	public Casa getCasa (Point P) {
		if (Tabuleiro.estaDentro (P))
			return casa[(int) P.getY ()][(int) P.getX ()];
		else
			return null;
	}
	public Casa getCasa (int i, int j) {
		if (Tabuleiro.estaDentro (i, j))
			return casa[i][j];
		else
			return null;
	}
	public Point getReizaum (Cor cor) {
		if (cor == Cor.BRANCO)
			return rei_branco;
		else
			return rei_preto;
	}
}
