/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;

import javax.swing.ImageIcon;

public class Cavalo extends Peca {
	
	public Cavalo (Cor nova_cor) {
		super (nova_cor);
	}
	
	/* GETTER */
	public ImageIcon getIcone () {
		return Icone.CAVALO.getImg (cor);
	}
}
