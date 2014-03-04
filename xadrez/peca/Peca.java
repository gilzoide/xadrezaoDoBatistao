/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 04/03/2014
 */
package xadrez.peca;

import ui.Cor;

public abstract class Peca {
	private Cor cor;
	
	/**
	 * Ctor: ajusta a cor
	 */
	public Peca (Cor nova_cor) {
		cor = nova_cor;
	}
}
