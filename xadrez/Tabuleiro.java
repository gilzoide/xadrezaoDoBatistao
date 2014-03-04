/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 04/03/2014
 */
package xadrez;

import ui.Cor;
import xadrez.peca.*;

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
}


/**
 * Cada casa do tabuleiro: tem uma cor e possivelmente uma peça
 */
class Casa {
	private Cor cor;
	private Peca peca;
	
	/**
	 * Ctor: depende da posição no tabuleiro, 
	 */
	public Casa (int i, int j) {
		// Únicas posições possíveis são de 0~7
		if (i >= 8 || j >= 8) {
			System.err.println ("Cê tá loko? Xadrez é só 8x8, irmão!");
			System.exit (-1);
		}
		
		// Cor certa, alternando
		cor = ((i + j) % 2 == 0) ? Cor.BRANCO: Cor.PRETO;

		switch (i) {
			// Peças PRETAS: primeira fila (torres, cavalos, bispos, dama e rei)
			case 0:
				if (j % 7 == 0)
					peca = new Torre (Cor.PRETO);
				else if (j % 5 == 1)
					peca = new Cavalo (Cor.PRETO);
				else if (j % 3 == 2)
					peca = new Bispo (Cor.PRETO);
				else if (j == 4)
					peca = new Dama (Cor.PRETO);
				else
					peca = new Rei (Cor.PRETO);
				break;
			// segunda fila (peões)
			case 1:
				peca = new Peao (Cor.PRETO);
				break;
			
			// Peças BRANCAS: segunda fila (peões)
			case 6:
				peca = new Peao (Cor.BRANCO);
				break;
			// primeira fila ((torres, cavalos, bispos, dama e rei)
			case 7:
				if (j % 7 == 0)
					peca = new Torre (Cor.BRANCO);
				else if (j % 5 == 1)
					peca = new Cavalo (Cor.BRANCO);
				else if (j % 3 == 2)
					peca = new Bispo (Cor.BRANCO);
				else if (j == 4)
					peca = new Dama (Cor.BRANCO);
				else
					peca = new Rei (Cor.BRANCO);
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
