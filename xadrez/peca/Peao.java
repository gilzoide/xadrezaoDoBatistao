/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 09/03/2014
 */
package xadrez.peca;

import ui.Cor;

public class Peao extends Peca {
	private boolean en_passant;
	
	public Peao (Cor nova_cor) {
		super (nova_cor);
		en_passant = false;
	}	
}
