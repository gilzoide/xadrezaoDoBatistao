/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;

import xadrez.Movimento;
import xadrez.Casa;
import xadrez.Tabuleiro;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Peao extends Peca {
	private boolean en_passant;
	
	public Peao (Cor nova_cor) {
		super (nova_cor);
		en_passant = false;
	}
	
	public ArrayList<Movimento> possiveisMovimentos (byte linha, byte coluna) {
		ArrayList<Movimento> aux = new ArrayList<>();
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		
		if (cor == Cor.PRETO)
			aux.add (new Movimento (tab.getCasa (linha, coluna), tab.getCasa (linha + 1, coluna)));
		else
			aux.add (new Movimento (tab.getCasa (linha, coluna), tab.getCasa (linha - 1, coluna)));
		
		return aux;
	}
	
	/* GETTER */
	public ImageIcon getIcone () {
		return Icone.PEAO.getImg (cor);
	}
}
