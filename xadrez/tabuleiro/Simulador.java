/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 18/04/2014
 */
package xadrez.tabuleiro;

import xadrez.movimento.Movimento;

import java.awt.Point;

import java.lang.Thread;

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
public class Simulador extends Thread {
	private Casa casa[][];
	private Casa donde, pronde;
	private Movimento mov;	/// Movimento a ser simulado
	
	public Simulador (Casa donde, Casa pronde) {
		casa = new Casa[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				casa[i][j] = new Casa (i, j);
			}
		}
		
		// Casas do simulador ao invés das reais, do tabuleiro
		this.donde = casa[(int) donde.getCoord ().getY ()][(int) donde.getCoord ().getX ()];
		this.pronde = casa[(int) pronde.getCoord ().getY ()][(int) pronde.getCoord ().getX ()];
		// cria o movimento nas casas simuladas
		this.mov = new Movimento (this.donde, this.pronde);
	}
	
	/**
	 * Primeiro precisamos copiar o tabuleiro pra podermos brincar direito
	 */
	public void copiaTabuleiro () {
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				Casa.copia (casa[i][j], tab.getCasa (i, j));
		}
	}
	
	// Simulação!
	@Override
	public void run () {
		
	}
	
	/* GETTERS */
	public Casa getCasa (Point P) {
		return casa[(int) P.getY ()][(int) P.getX ()];
	}
}
