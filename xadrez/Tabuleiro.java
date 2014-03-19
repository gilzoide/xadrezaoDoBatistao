/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package xadrez;

import ui.Cor;
import xadrez.peca.Peca;

/**
 * Tabuleiro: padrão singleton em POO, já que o tabuleiro nosso é único e global
 */
public class Tabuleiro {
	// a única instância de tabuleiro permitida
	private static Tabuleiro tabuleiro = new Tabuleiro ();
	// quer o tabuleiro? usa esse método aqui
	public static Tabuleiro getTabuleiro () {
		return tabuleiro;
	}

	// as casas do tabuleiro, matriz 8x8
	private static Casa[][] casa;

	/**
	 * Ctor: constrói cada casa do tabuleiro
	 */
	private Tabuleiro () {
		// nosso tabuleiro 8x8
		casa = new Casa[8][8];

		for (byte i = 0; i < 8; i++) {
			for (byte j = 0; j < 8; j++) {
				casa[i][j] = new Casa (i, j);
			}
		}
	}

	/* GETTERS */
	public Casa getCasa (byte linha, byte coluna) {
		return this.casa[linha][coluna];
	}

	/* Posição está dentro do tabuleiro? */
	public static boolean estaDentro (byte linha, byte coluna) {
		if ((linha >= 0 && linha < 8) && (coluna >= 0 && coluna < 8))
			return true;
		else
			return false;
	}
	/* Posição do tabuleiro está ocupada? */
	public static boolean estaOcupado (byte linha, byte coluna) {
		if (estaDentro (linha, coluna) && casa[linha][coluna].getPeca () != null)
			return true;
		else
			return false;
	}
	/* Posição do tabuleiro está ocupada pela cor contrária? */
	public static boolean estaOcupadoPeloInimigo (byte linha, byte coluna, Cor cor_minha) {
		if (estaDentro (linha, coluna)) {
			Peca aux = casa[linha][coluna].getPeca ();
			return (cor_minha.ehCorOposta (aux.getCor ()));
		}
		else
			return false;
	}
}
