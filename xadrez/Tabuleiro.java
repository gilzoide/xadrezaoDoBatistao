/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 03/03/2014
 */
package xadrez;

import ui.Cor;

public class Tabuleiro {
	Casa[][] casa = new Casa[8][8];
	
	public Tabuleiro () {
		// colore o tabuleiro do jeito que deve
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				casa[i][j] = new Casa (i, j);
			}
		}
	}
	
	public void PrintTabuleiro () {
		
	}
}


/**
 * Cada casa do tabuleiro: tem uma cor e possivelmente uma peça
 */
class Casa {
	private Cor cor;
	private Peca peca;
	
	/**
	 * Ctor: dependendo da posição no tabuleiro, 
	 */
	public Casa (int i, int j) {
		if (i > 8 || j > 8) {
			System.err.println ("Cê tá loko? Xadrez é só 8x8, irmão!");
			System.exit (-1);
		}
		
		// Cor certa, alternando
		cor = ((i + j) % 2 == 0) ? Cor.BRANCO: Cor.PRETO;

		switch (i) {
			// Peças PRETAS: primeira fila (torres, cavalos, bispos, dama e rei)
			case 0:
			
				break;
			// segunda fila (peões)
			case 1:
				
				break;
			
			// Peças BRANCAS: segunda fila (peões)
			case 6:
			
				break;
			// primeira fila ((torres, cavalos, bispos, dama e rei)
			case 7:
			
				break;
			
			default:
				peca = null;
		}
	}
	
	/* GETTERS */
	public Cor getCor () {
		return cor;
	}
	public Peca getPeca () {
		return peca;
	}
	/* SETTER */
	public void setPeca (Peca nova_peca) {
		peca = nova_peca;
	}
}
