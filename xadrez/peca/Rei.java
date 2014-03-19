/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;

import javax.swing.ImageIcon;

public class Rei extends Peca {
	/* Esse rei pode fazer roque? */
	private boolean roque;
	
	public Rei (Cor nova_cor) {
		super (nova_cor);
		roque = true;
	}	
	
	/* GETTER */
	public ImageIcon getIcone () {
		return Icone.REI.getImg ();
	}
}
