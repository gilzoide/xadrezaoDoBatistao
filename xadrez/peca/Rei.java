/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;

import xadrez.Casa;
import xadrez.Tabuleiro;
import xadrez.Movimento;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Rei extends Peca {
	/* Esse rei pode fazer roque? */
	private boolean roque;
	
	public Rei (Cor nova_cor) {
		super (nova_cor);
		roque = true;
	}
	
	public ArrayList<Movimento> possiveisMovimentos (byte linha, byte coluna) {
		ArrayList<Movimento> aux = new ArrayList<>();
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		
		aux.add (new Movimento (tab.getCasa (linha, coluna), tab.getCasa (linha + 1, coluna)));
		
		return aux;
	}
	
	/* GETTER */
	public ImageIcon getIcone () {
		return Icone.REI.getImg (cor);
	}
}
