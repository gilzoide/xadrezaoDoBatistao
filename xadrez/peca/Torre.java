/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;

import javax.swing.ImageIcon;

public class Torre extends Peca {
	public Torre (Cor nova_cor) {
		super (nova_cor);
	}	
	
	/* GETTER */
	public ImageIcon getIcone () {
		return Icone.TORRE.getImg ();
	}
}
