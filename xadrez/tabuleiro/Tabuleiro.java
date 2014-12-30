/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package xadrez.tabuleiro;


import java.awt.Point;

/**
 * Tabuleiro: padrão singleton em POO, já que o tabuleiro nosso é único e global
 */
public class Tabuleiro {
	// a única instância de tabuleiro permitida
	private static Tabuleiro tabuleiro = new Tabuleiro ();

	// as casas do tabuleiro, matriz 8x8
	private static Casa[][] casa;

	/**
	 * Ctor: constrói cada casa do tabuleiro
	 */
	private Tabuleiro () {
		// nosso tabuleiro 8x8
		casa = new Casa[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				casa[i][j] = new Casa (i, j);
		}
	}
	// quer o tabuleiro? usa esse método aqui
	public static Tabuleiro getTabuleiro () {
		return tabuleiro;
	}

	/* GETTERS */
	public Casa getCasa (int linha, int coluna) {
		if (estaDentro (linha, coluna))
			return Tabuleiro.casa[linha][coluna];
		else
			return null;
	}
	public Casa getCasa (Point P) {
		if (estaDentro (P))
			return Tabuleiro.casa[(int) P.getY ()][(int) P.getX ()];
		else
			return null;
	}

	/* Posição está dentro do tabuleiro? */
	public static boolean estaDentro (int linha, int coluna) {
		if ((linha >= 0 && linha < 8) && (coluna >= 0 && coluna < 8))
			return true;
		else
			return false;
	}
	public static boolean estaDentro (Point P) {
		if ((P.getX () >= 0 && P.getX () < 8) && (P.getY () >= 0 && P.getY () < 8))
			return true;
		else
			return false;
	}
	/* Posição do tabuleiro está ocupada? */
	public static boolean estaOcupado (Point P) {
		if (estaDentro (P) && casa[(int) P.getX ()][(int) P.getY ()].estaOcupada ())
			return true;
		else
			return false;
	}
	
	/**
	 * Reposiciona as peças para um novo jogo
	 */
	public void novoJogo () {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				casa[i][j].casaNovoJogo ();
		}
	}
	
	
	public void printDominio () {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				System.out.println (casa[i][j].getDominio ());
		}
	}
	
	
	/**
	 * Reprinta todo o tabuleiro
	 */
	public void refreshTabuleiro () {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				casa[i][j].atualizaIcone ();
		}
	}
	
	public void printTabuleiro () {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				System.out.print (casa[i][j].getPeca ());
			
			System.out.print ('\n');
		}
	}
}
